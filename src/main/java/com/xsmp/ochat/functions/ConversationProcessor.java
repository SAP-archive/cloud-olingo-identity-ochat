package com.xsmp.ochat.functions;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;

import org.apache.olingo.odata2.api.annotation.edm.EdmFacets;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport.ReturnType;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport.HttpMethod;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport.ReturnType.Type;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImportParameter;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.jpa.processor.core.ODataJPAContextImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xsmp.id.IdentityInteraction;
import com.xsmp.ochat.model.Conversation;
import com.xsmp.ochat.model.MessageUser;
import com.xsmp.ochat.util.Utility;

public class ConversationProcessor {
	
	private static Logger logger = LoggerFactory.getLogger(ConversationProcessor.class);

	public ConversationProcessor() {
	}

	/**
	 * Return a Conversation entity representing a two-party connection between the authenticated user and the
	 * specified target user id.  If such a two-way Conversation already exists, return that, otherwise return a new Conversation.
	 * 
	 * @param targetMessageUserId the OChat user entity to connect with
	 * @return the two-way Conversation entity
	 * @throws ODataException
	 */
	@EdmFunctionImport(
			returnType = @ReturnType(type = Type.ENTITY, isCollection = false), 
			entitySet = "Conversations", 
			httpMethod = HttpMethod.GET)
	public Conversation createConversation(
			@EdmFunctionImportParameter(name = "TargetUserId", facets = @EdmFacets(nullable = false)) final Integer targetMessageUserId)
					throws ODataException {
		
		Conversation result = null;
		EntityManager em = Utility.getEntityManagerFactory().createEntityManager();

		if (targetMessageUserId <= 0) {
			throw new ODataException("Invalid TargetUserId");
		}
		
		/*
		 * Get the authenticated username
		 */
		ODataContext ctx = ODataJPAContextImpl.getContextInThreadLocal();
		HttpServletRequest r = (HttpServletRequest) ctx.getParameter(ODataContext.HTTP_SERVLET_REQUEST_OBJECT);
		String user = r.getRemoteUser();
		MessageUser u1 = null;
		if (user != null) {
			u1 = IdentityInteraction.verifyUser(r, em);
			logger.debug( "username from HttpServletRequest '"+u1.getUsername()+"'" );
		}
		else {
			// This error will have the side effect of returning an empty result set
			logger.error("Assertion error: There is no authenticated user defined in the HttpServletRequest -- " +
					     "check your web.xml application configuration");
			throw new ODataException("There is no authenticated user defined in the HttpServletRequest -- " +
				     "check your web.xml application configuration");
		}
		
		// Determine if there is already a conversation that
		// includes exactly the target user and the authenticated user.
		// If so, return that without making and changes to data.
		// If not, create a new Conversation.
		TypedQuery<Conversation> queryBP;
		try {
			// Select all Conversations where the authenticated user is a member.
			queryBP = em.createQuery("SELECT DISTINCT E1 from Conversation E1 JOIN E1.messageUsers E0 " + 
							         "WHERE E0.username = :user ORDER BY E1.conversationId", 
							         Conversation.class);
			queryBP.setParameter("user", user);
			
			// Look for a Conversation that contains only the authenticated and target user
			boolean found = false;
			for (Conversation c : queryBP.getResultList()) {
				while (!found) {
					// must be a two person conversation
					if (c.getMessageUsers().size() == 2) {
						for (MessageUser u : c.getMessageUsers()) {
							if (u.getUserId() == targetMessageUserId.intValue()) {
								found = true;
								result = c;
								break;
							}
						}
					}
				}
			}
			
			// If a two-person conversation didn't exist, create it
			if (!found) {
				
				MessageUser u2 = em.find(MessageUser.class, targetMessageUserId);
				
				if (u2 == null) {
					throw new ODataException("Invalid TargetUserId");
				}
				
				em.getTransaction().begin();
				
				result = new Conversation();
				result.setName(u1.getDisplayableName() + ", " + u2.getDisplayableName());
				result.setMessageUsers(new ArrayList<MessageUser>());
				result.getMessageUsers().add(u1);
				result.getMessageUsers().add(u2);
				em.persist(result);
				
				em.getTransaction().commit();
			}
		}
		catch (Exception e) {
			throw new ODataException(e.getMessage());
		}

		return result;
	}

}
