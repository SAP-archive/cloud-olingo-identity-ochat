package com.xsmp.ochat;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
//import javax.transaction.UserTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xsmp.id.IdentityInteraction;
import com.xsmp.ochat.model.Message;
import com.xsmp.ochat.model.Conversation;
import com.xsmp.ochat.model.MessageUser;

public class DataLoader {
	private static Logger logger = LoggerFactory.getLogger(DataLoader.class);
	private MessageUser u1;
	private MessageUser u2;
	private MessageUser u3;
	
	private Conversation g1;
	private Conversation g2;
	private Conversation g3;
	private Conversation g4;

	private EntityManagerFactory emf;

	public DataLoader(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	/**
	 * Load a modest test data set
	 */
	public void loadData() {
		loadUsers();
		loadGroups();
		loadMessages();
	}
	
	private void loadMessages() {
		EntityManager em = emf.createEntityManager();
		TypedQuery<Message> queryBP;
		List<Message> resBP;
		try {
			//UserTransaction transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
			//transaction.begin();
			em.getTransaction().begin();
			queryBP = em.createQuery("SELECT c FROM Message c", Message.class);
			resBP = queryBP.getResultList();
			if (resBP.size() > 1) {
				logger.info(resBP.size()
						+ " Messages already available in the db");
			} else {
				
				// Use the IdentityInteraction class to simulate a specific logged-in user
				IdentityInteraction.setThreadLocalAuthenticatedUser(u1);
				Message m = new Message();
				m.setText("Hi all!");
				//m.setSender(u1);
				m.setParentGroup(g1);
				em.persist(m);
				
				g1.getMessages().add(m);
				
				IdentityInteraction.setThreadLocalAuthenticatedUser(u2);
				m = new Message();
				m.setText("Hi back!");
				//m.setSender(u2);
				m.setParentGroup(g1);
				em.persist(m);
				
				g1.getMessages().add(m);
				
				IdentityInteraction.setThreadLocalAuthenticatedUser(u3);
				m = new Message();
				m.setText("Just another static test");
				//m.setSender(u3);
				m.setParentGroup(g1);
				em.persist(m);
				
				g1.getMessages().add(m);
				
				IdentityInteraction.setThreadLocalAuthenticatedUser(u1);
				m = new Message();
				m.setText("A group2 message; hello, Bob");
				//m.setSender(u1);
				m.setParentGroup(g2);
				em.persist(m);
				
				g1.getMessages().add(m);
				
				em.getTransaction().commit();
				//transaction.commit();
				queryBP = em.createQuery("SELECT c FROM Message c",
						Message.class);
				resBP = queryBP.getResultList();
				logger.info(resBP.size() + " Messages loaded into the db");
			}
		} catch (Exception e) {
			logger.error("Exception occured", e);
		} finally {
			em.close();
		}
	}

	public void loadUsers() {
		EntityManager em = emf.createEntityManager();
		TypedQuery<MessageUser> queryBP;
		List<MessageUser> resBP;
		try {
			//UserTransaction transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
			//transaction.begin();
			em.getTransaction().begin();
			queryBP = em
					.createQuery("SELECT c FROM MessageUser c", MessageUser.class);
			resBP = queryBP.getResultList();
			if (resBP.size() > 1) {
				logger.info(resBP.size()
						+ " MessageUsers already available in the db");
			} else {
				u1 = new MessageUser();
				u1.setUsername("bob.braun@nowhere.com");
				u1.setFirstName("Bob");
				u1.setLastName("Braun");
				u1.setEmail("bob.braun@nowhere.com");
				em.persist(u1);
				
				u2 = new MessageUser();
				u2.setUsername("alice.anders@nowhere.com");
				u2.setFirstName("Alice");
				u2.setLastName("Anders");
				u2.setEmail("alice.anders@nowhere.com");
				em.persist(u2);
				
				u3 = new MessageUser();
				u3.setUsername("charles.crew@nowhere.com");
				u3.setFirstName("Charles");
				u3.setLastName("Crew");
				u3.setEmail("charles.crew@nowhere.com");
				em.persist(u3);
				
				em.getTransaction().commit();
				//transaction.commit();
				queryBP = em.createQuery("SELECT c FROM MessageUser c",
						MessageUser.class);
				resBP = queryBP.getResultList();
				logger.info(resBP.size() + " MessageUsers loaded into the db");
			}
		} catch (Exception e) {
			logger.error("Exception occured", e);
		} finally {
			em.close();
		}
	}
	
	public void loadGroups() {
		EntityManager em = emf.createEntityManager();
		TypedQuery<Conversation> queryBP;
		List<Conversation> resBP;
		try {
			//UserTransaction transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
			//transaction.begin();
			em.getTransaction().begin();
			queryBP = em
					.createQuery("SELECT c FROM Conversation c", Conversation.class);
			resBP = queryBP.getResultList();
			if (resBP.size() > 1) {
				logger.info(resBP.size()
						+ " Conversations already available in the db");
			} else {
				// Use the IdentityInteraction class to simulate a specific logged-in user
				IdentityInteraction.setThreadLocalAuthenticatedUser(u1);
				g1 = new Conversation();
				g1.setName("alice, bob, charlie");
				g1.setMessageUsers(new ArrayList<MessageUser>());
				g1.getMessageUsers().add(u1);
				g1.getMessageUsers().add(u2);
				g1.getMessageUsers().add(u3);
				em.persist(g1);
				
				// Use the IdentityInteraction class to simulate a specific logged-in user
				IdentityInteraction.setThreadLocalAuthenticatedUser(u1);
				g2 = new Conversation();
				g2.setName("alice, bob");
				g2.setMessageUsers(new ArrayList<MessageUser>());
				g2.getMessageUsers().add(u1);
				g2.getMessageUsers().add(u2);
				em.persist(g2);
				
				// Use the IdentityInteraction class to simulate a specific logged-in user
				IdentityInteraction.setThreadLocalAuthenticatedUser(u2);
				g3 = new Conversation();
				g3.setName("alice, charlie");
				g3.setMessageUsers(new ArrayList<MessageUser>());
				g3.getMessageUsers().add(u2);
				g3.getMessageUsers().add(u3);
				em.persist(g3);
				
				// Use the IdentityInteraction class to simulate a specific logged-in user
				IdentityInteraction.setThreadLocalAuthenticatedUser(u3);
				g4 = new Conversation();
				g4.setName("bob, charlie");
				g4.setMessageUsers(new ArrayList<MessageUser>());
				g4.getMessageUsers().add(u1);
				g4.getMessageUsers().add(u3);
				em.persist(g4);
				
				em.getTransaction().commit();
				//transaction.commit();
				queryBP = em.createQuery("SELECT c FROM Conversation c",
						Conversation.class);
				resBP = queryBP.getResultList();
				logger.info(resBP.size() + " Conversations loaded into the db");
			}
		} catch (Exception e) {
			logger.error("Exception occured", e);
		} finally {
			em.close();
		}
	}
}
