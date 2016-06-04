package com.tigersndragons.docbookmarks.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(schema="DOCBOOKMARKS",name="USER_ATTEMPTS")
@AttributeOverride(name="id", column=@Column(name="USER_ATTEMPTS_ID"))
public class UserAttempts 
extends V3Object {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private Integer attempts;
	private DateTime lastModified;
	
	@Column(name="USER_NAME")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name="ATTEMPTS")
	public Integer getAttempts(){
		return attempts;
	}
	public void setAttempts(Integer attempts){
		this.attempts = attempts;
	}

	@Column(name="LASTMODIFIED")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getLastModified() {
		return lastModified;
	}
	public void setLastModified(DateTime lastModified) {
		this.lastModified = lastModified;
	}
}
