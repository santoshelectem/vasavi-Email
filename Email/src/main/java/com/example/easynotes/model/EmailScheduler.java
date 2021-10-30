/**
 * 
 */
package com.example.easynotes.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * @author Electem2
 *
 */
@Entity
public class EmailScheduler {
	
    /**
     * emailScheduleId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emailScheduleId;
    /**
     * emailSubject
     */
    private String emailSubject;
    /**
     * userId
     */
	
	  @OneToOne
	  @JoinColumn(name ="userId") 
	  private User user;
	 
	
	  /**
	     * managerUserIds
	     */
    private String managerUserIds;
    /**
     * sendStatus
     */
    private boolean sendStatus;
    
	

	/**
	 * @return the emailScheduleId
	 */
	public Long getEmailScheduleId() {
		return emailScheduleId;
	}

	/**
	 * @param emailScheduleId the emailScheduleId to set
	 */
	public void setEmailScheduleId(final Long emailScheduleId) {
		this.emailScheduleId = emailScheduleId;
	}

	/**
	 * @return the emailSubject
	 */
	public String getEmailSubject() {
		return emailSubject;
	}

	/**
	 * @param emailSubject the emailSubject to set
	 */
	public void setEmailSubject(final String emailSubject) {
		this.emailSubject = emailSubject;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(final User user) {
		this.user = user;
	}

	/**
	 * @return the managerUserIds
	 */
	public String getManagerUserIds() {
		return managerUserIds;
	}

	/**
	 * @param managerUserIds the managerUserIds to set
	 */
	public void setManagerUserIds(final String managerUserIds) {
		this.managerUserIds = managerUserIds;
	}

	/**
	 * @return the sendStatus
	 */
	public boolean isSendStatus() {
		return sendStatus;
	}

	/**
	 * @param sendStatus the sendStatus to set
	 */
	public void setSendStatus(final boolean sendStatus) {
		this.sendStatus = sendStatus;
	}

	@Override
	public String toString() {
		return "EmailScheduler [emailScheduleId=" + emailScheduleId + ", emailSubject=" + emailSubject + ", user="
				+ user + ", managerUserIds=" + managerUserIds + ", sendStatus=" + sendStatus + "]";
	}

	/**
	 * @param emailScheduleId
	 * @param emailSubject
	 * @param user
	 * @param managerUserIds
	 * @param sendStatus
	 */
	public EmailScheduler(final Long emailScheduleId,final String emailSubject,final User user,final String managerUserIds,
			final boolean sendStatus) {
		super();
		this.emailScheduleId = emailScheduleId;
		this.emailSubject = emailSubject;
		this.user = user;
		this.managerUserIds = managerUserIds;
		this.sendStatus = sendStatus;
	}

	/**
	 * 
	 */
	public EmailScheduler() {
		super();
	}

    
}
