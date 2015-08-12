package com.xsmp.ochat.util;

import java.util.Calendar;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.xsmp.ochat.model.Message;

/**
 * This JPA Object represents a push notification to be sent to the members of a Conversation.
 * 
 * @author Riley Rainey <riley.rainey@sap.com>
 */
@Entity
@Table(name = "OCHAT_PENDING_NOTIFICATION")
public class PendingNotification {
	@TableGenerator(name = "PendingNotificationGenerator", table = "OCHAT_ID_GENERATOR", 
			pkColumnName = "GENERATOR_NAME", valueColumnName = "GENERATOR_VALUE", 
			pkColumnValue = "PendingNotification", initialValue = 1, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "NotificationGenerator")
	@Column(name = "PENDING_NOTIFICATION_ID")
	private Integer pendingNotificationId;
	
	@OneToOne(optional = false)
	@Column(name = "MESSAGE_ID")
	private Message message;

	@Basic(optional = false)
	@Temporal(TemporalType.TIMESTAMP)
	protected Calendar updatedTimestamp;
	
	@PrePersist
	@PreUpdate
	private void persist() {
		Calendar calendar = Calendar.getInstance();
		// Use the server time as the time of the message
		setUpdatedTimestamp( calendar );
	}

	public Integer getPendingNotificationId() {
		return pendingNotificationId;
	}

	public void setPendingNotificationId(Integer pendingNotificationId) {
		this.pendingNotificationId = pendingNotificationId;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Calendar getUpdatedTimestamp() {
		return updatedTimestamp;
	}

	public void setUpdatedTimestamp(Calendar updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}

}