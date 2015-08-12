package com.xsmp.ochat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;

/**
 * This JPA Object represents an SMP application to be notified when an interesting change in the back-end data occurs.
 * Currently, the only "interesting event" is an update of any Product record.  If other interesting events were defined,
 * an EVENT_ID column could be added to this object to allow for notifications of different events to target specific
 * applications.
 * 
 * @author Riley Rainey <riley.rainey@sap.com>
 * @see com.xsmp.ochat.util.MessagePushNotificationTrigger
 */
@Entity
@Table(name = "OCHAT_NOTIFICATION_TARGET", uniqueConstraints=@UniqueConstraint(columnNames = {"HOSTNAME", "APPLICATION_NAME"}))
public class NotificationTarget {
	@TableGenerator(name = "NotificationTargetGenerator", table = "OCHAT_ID_GENERATOR", pkColumnName = "GENERATOR_NAME", valueColumnName = "GENERATOR_VALUE", pkColumnValue = "NotificationTarget", initialValue = 1, allocationSize = 10)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "NotificationGenerator")
	@Column(name = "NOTIFICATION_TARGET_ID")
	private Integer notficationtargetId;

	@Column(name = "HOSTNAME", length = 128, nullable=false)
	private String hostname;

	@Column(name = "PORT", nullable=false)
	private Integer port;
	
	@Column(name = "USE_HTTPS", nullable=false)
	private Integer https;

	@Column(name = "NOTIFICATION_USER", length = 128, nullable=false)
	private String notificationUser;

	@Column(name = "NOTIFICATION_PASSWORD", length = 128)
	private String password;
	
	@Column(name = "APPLICATION_NAME", length = 256, nullable=false)
	private String applicationName;

	public Integer getNotficationtargetId() {
		return notficationtargetId;
	}
	
	public void setNotficationtargetId(Integer notficationtargetId) {
		this.notficationtargetId = notficationtargetId;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getHttps() {
		return https;
	}

	public void setHttps(Integer https) {
		this.https = https;
	}

	// This getter tricks Olingo into being 
	// unable to return the value in a web service
	public String getNotificationUser() {
		return "******";
	}
	
	public String getActualNotificationUser() {
		return notificationUser;
	}

	public void setNotificationUser(String username) {
		this.notificationUser = username;
	}

	// This getter tricks Olingo into being 
	// unable to return the value in a web service
	public String getPassword() {
		return "******";
	}
	
	public String getActualPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

}