package com.xsmp.ochat.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "OCHAT_MESSAGE_USER")
public class MessageUser {

	@TableGenerator(name = "UserGenerator", table = "OCHAT_ID_GENERATOR", pkColumnName = "GENERATOR_NAME", 
			valueColumnName = "GENERATOR_VALUE", pkColumnValue = "MessageUser", initialValue = 1000, allocationSize = 1,
			indexes={ @Index(name = "IDX_USERNAME", columnList = "username") })
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "UserGenerator")
	@Column(name = "MESSAGE_USER_ID")
	private Integer userId;

	// Username actually ends up being the SAML NameID (which is typically the user's e-mail by HCP usage, but it might be anything)
	// HCP uses this as a displayed name in several places, but that might not be the best idea for our application.
	// it is called "username" here only because that's what returned by getRemoteUser() in an HCP Java Application
	@Column(name = "USERNAME", unique = true)
	private String username;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "EMAIL", unique = true)
	private String email;
	
	@ManyToMany(mappedBy="messageUsers")
	private List<Conversation> conversations;
	
	@Basic(optional = false)
	@Temporal(TemporalType.TIMESTAMP)
	protected Calendar updatedTimestamp;

	
	@PrePersist
	@PreUpdate
	private void persist() {
		Calendar calendar = Calendar.getInstance();
		setUpdatedTimestamp( calendar );
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Calendar getUpdatedTimestamp() {
		return updatedTimestamp;
	}

	public void setUpdatedTimestamp(Calendar updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Conversation> getConversations() {
		return conversations;
	}

	public void setConversations(List<Conversation> conversations) {
		this.conversations = conversations;
	}
	
	public String getDisplayableName() {
		return firstName + " " + lastName;
	}
}