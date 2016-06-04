package com.tigersndragons.docbookmarks.model;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
 
@Entity
@Table(name = "USER_ROLE", schema = "DOCBOOKMARKS", 
	uniqueConstraints = @UniqueConstraint(
		columnNames = { "ROLE", "USER_NAME" }))
public class UserRole{
 
	private Integer userRoleId;
	private SecurityUser user;
	private String role;
 
	public UserRole() {
	}
 
	public UserRole(SecurityUser user, String role) {
		this.user = user;
		this.role = role;
	}
 
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "USER_ROLE_ID", 
		unique = true, nullable = false)
	public Integer getUserRoleId() {
		return this.userRoleId;
	}
 
	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}
 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_NAME", nullable = false)
	public SecurityUser getUser() {
		return this.user;
	}
 
	public void setUser(SecurityUser user) {
		this.user = user;
	}
 
	@Column(name = "ROLE", nullable = false, length = 45)
	public String getRole() {
		return this.role;
	}
 
	public void setRole(String role) {
		this.role = role;
	}
 
}
