package com.xsmp.ochat.model;

import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.xsmp.id.IdentityInteraction;

@Entity
@Table(name = "OCHAT_MESSAGE")
@EntityListeners(com.xsmp.ochat.MessageQueryListener.class)
public class Message {

	/* Customer ids are generated starting with 1 */
	@TableGenerator(name = "MessageGenerator", table = "OCHAT_ID_GENERATOR", pkColumnName = "GENERATOR_NAME", 
			valueColumnName = "GENERATOR_VALUE", pkColumnValue = "Message", initialValue = 1, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MessageGenerator")
	@Column(name = "MESSAGE_ID")
	private long messageId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CONVERSATION_ID", nullable=false)
	private Conversation conversation;

	@Column(name = "TEXT", length = 1024, nullable=false)
	private String text;

	@JoinColumn(name = "SENDER", nullable=false)
	private MessageUser sender;
	
	@Column(name = "SENDER_TZ", length = 16)
	private String senderTimezone;
	
	@Column(name = "SENDER_LATITIDE")
	private Double latitide;
	
	@Column(name = "SENDER_LONGITIDE")
	private Double longitide;
	
	@Column(name = "SENDER_LOCATION_CERTAINTY")
	private int locationCertainity;
	
	@Basic(optional = false)
	@Temporal(TemporalType.TIMESTAMP)
	protected Calendar updatedTimestamp;
	
	@PrePersist
	@PreUpdate
	private void persist() {
		Calendar calendar = Calendar.getInstance();
		// Use the server time as the time of the message
		setUpdatedTimestamp( calendar );
		// The identity of the sender is established by the identity of the logged-in user.
		setSender( IdentityInteraction.getThreadLocalAuthenticatedUser() );
	}

	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setParentGroup(Conversation parentGroup) {
		this.conversation = parentGroup;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public MessageUser getSender() {
		return sender;
	}

	public void setSender(MessageUser sender) {
		this.sender = sender;
	}

	public Calendar getUpdatedTimestamp() {
		return updatedTimestamp;
	}

	public void setUpdatedTimestamp(Calendar updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}

	public String getSenderTimezone() {
		return senderTimezone;
	}

	public void setSenderTimezone(String senderTimezone) {
		this.senderTimezone = senderTimezone;
	}

	public Double getLatitide() {
		return latitide;
	}

	public void setLatitide(Double latitide) {
		this.latitide = latitide;
	}

	public Double getLongitide() {
		return longitide;
	}

	public void setLongitide(Double longitide) {
		this.longitide = longitide;
	}

	public int getLocationCertainity() {
		return locationCertainity;
	}

	public void setLocationCertainity(int locationCertainity) {
		this.locationCertainity = locationCertainity;
	}
}