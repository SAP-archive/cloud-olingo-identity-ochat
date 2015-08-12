package com.xsmp.ochat.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//import com.xsmp.id.IdentityInteraction;

@Entity
@Table(name = "OCHAT_CONVERSATION")
@EntityListeners(com.xsmp.ochat.ConversationQueryListener.class)
public class Conversation {
	
	public Conversation() {
		messages = new ArrayList<Message>();
		messageUsers = new ArrayList<MessageUser>();
	}

	@TableGenerator(name = "ConversationGenerator", table = "OCHAT_ID_GENERATOR", pkColumnName = "GENERATOR_NAME", 
			valueColumnName = "GENERATOR_VALUE", pkColumnValue = "Conversation", initialValue = 1, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MessageGroupGenerator")
	@Column(name = "CONVERSATION_ID")
	private long conversationId;
	
	@Column(name = "NAME")
	private String name;
	
	//@JoinColumn(name = "CREATOR", nullable=false)
	//private MessageUser creator;
	
	@ManyToMany
	@JoinTable(
	      name="OCHAT_USER_CONVERSATION",
	      joinColumns={@JoinColumn(name="CONVERSATION_ID", referencedColumnName="CONVERSATION_ID")},
	      inverseJoinColumns={@JoinColumn(name="MESSAGE_USER_ID", referencedColumnName="MESSAGE_USER_ID")})
	private List<MessageUser> messageUsers;
	
	@OneToMany(mappedBy="conversation")
	private List<Message> messages;
	
	@Basic(optional = false)
	@Temporal(TemporalType.TIMESTAMP)
	protected Calendar updatedTimestamp;

	@PrePersist
	private void persist() {
		Calendar calendar = Calendar.getInstance();
		setUpdatedTimestamp( calendar );
		// The identity of the sender is established by the identity of the logged-in user.
		//setCreator( IdentityInteraction.getThreadLocalAuthenticatedUser() );
	}
	
	@PreUpdate
	private void preUpdate() {
		Calendar calendar = Calendar.getInstance();
		setUpdatedTimestamp( calendar );
	}
	
	public void setConversationId(long conversationId) {
		this.conversationId = conversationId;
	}
	
	public long getConversationId() {
		return conversationId;
	}

	public List<MessageUser> getMessageUsers() {
		return messageUsers;
	}

	public void setMessageUsers(List<MessageUser> messageUsers) {
		this.messageUsers = messageUsers;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Calendar getUpdatedTimestamp() {
		return updatedTimestamp;
	}

	public void setUpdatedTimestamp(Calendar updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
/*
	public MessageUser getCreator() {
		return creator;
	}

	public void setCreator(MessageUser creator) {
		this.creator = creator;
	}
*/

}