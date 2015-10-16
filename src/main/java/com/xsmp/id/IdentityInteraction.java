package com.xsmp.id;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.security.um.user.PersistenceException;
import com.sap.security.um.user.UnsupportedUserAttributeException;
import com.sap.security.um.user.User;
import com.sap.security.um.user.UserProvider;
import com.xsmp.ochat.model.MessageUser;

/**
 * 
 * The system-of-record for user identities exists outside of this web service.
 * For example, when deployed in HCP, user identities will come from the SAML IdP connected to
 * the HANA Cloud Platform instance. The helper class is used to manage a table of users revealed to this application.
 * Users are "discovered" and dynamically created in the table by calling the <code>verifyUser()</code>
 * member of this class. Each of the web service endpoints contain code that invokes this help, as needed.
 * 
 * @author Riley Rainey <riley.rainey@sap.com>
 */
public class IdentityInteraction {
	
	private static Logger logger = LoggerFactory.getLogger(IdentityInteraction.class);
	
	/**
	 * Keep a copy of information regarding the authenticated user for each request in thread local storage.
	 * We may refer to this information multiple times while processing an OData service request.
	 * 
	 * This class has been tested with the HCP "Java Web Tomcat 7" server type
	 */
	private static final ThreadLocal<MessageUser> user = new ThreadLocal<MessageUser>();
	
	/**
	 * Lookup the user, creating a user record if it did not previously exist
	 *
	 * @param username - name of authenticated user (to be verified)
	 * @param em - JPA Entity Manager to be used to access user table
	 * @return the MessageUser object associated with the specified username
	 * @throws PersistenceException 
	 */
	public static MessageUser verifyUser(HttpServletRequest request, EntityManager em) {
		MessageUser result = null;
		
		String nameId = null;
		String email = "";
		String lastName = null;
		String firstName = null;
		
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			UserProvider userProvider;
			userProvider = (UserProvider) ctx.lookup("java:comp/env/user/Provider");
		
			User user = null;
			if (request.getUserPrincipal() != null) {
				
				nameId = request.getUserPrincipal().getName();
				user = userProvider.getUser(nameId);
			     
				if ( user != null) {
					try {
						email = user.getAttribute("email");
					} catch (UnsupportedUserAttributeException e) {
						// no error
					}
					try {
						lastName = user.getAttribute("lastname");
					} catch (UnsupportedUserAttributeException e) {
						// no error
					}
					try {
						firstName = user.getAttribute("firstname");
					} catch (UnsupportedUserAttributeException e) {
						// no error
					}
				}
			}
			
		} catch (NamingException e1) {
			logger.error("NamingException insde IntentityInteraction housekeeping: " + e1.getMessage());
			e1.printStackTrace();
		} catch (PersistenceException e2) {
			logger.error("PersistenceException insde IntentityInteraction housekeeping: " + e2.getMessage());
			e2.printStackTrace();
		}
		
		TypedQuery<MessageUser> userQuery;
		List<MessageUser> resultList;
		
		
		try {
			userQuery = em.createQuery("SELECT m FROM MessageUser m WHERE m.username = :user", MessageUser.class);
			userQuery.setParameter("user", nameId);
			resultList = userQuery.getResultList();
	
			// The username field is specified to be unique in the JPA definition of MessageUser, so we will
			// only have get one record back or nothing ...
			
			if (resultList.size() == 1) {
				result = resultList.get(0);
			}
			else {
				em.getTransaction().begin();
				MessageUser newUser = new MessageUser();
				newUser.setUsername(nameId);
				newUser.setFirstName(firstName);
				newUser.setLastName(lastName);
				newUser.setEmail(email);
				em.persist(newUser);
				
				em.getTransaction().commit();
				result = newUser;

			}
			setThreadLocalAuthenticatedUser(result);
		}
		catch (Exception e) {
			logger.error("Exception insde IntentityInteraction housekeeping: " + e.getMessage());
		}
		return result;
	}

	public static MessageUser getThreadLocalAuthenticatedUser() {
		return (MessageUser) user.get();
	}

	public static void setThreadLocalAuthenticatedUser(MessageUser user) {
		IdentityInteraction.user.set(user);
	}
}
