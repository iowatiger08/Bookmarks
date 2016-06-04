package com.tigersndragons.docbookmarks.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(schema="DOCBOOKMARKS",name="SECURITY_USER")
//@AttributeOverride(name="id", column=@Column(name="SECURITY_USER_ID"))
public class SecurityUser 
	//extends V3Object {
extends com.tigersndragons.docbookmarks.model.Entity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id ;
	private String userName;
	private String credentials;
	private Integer accountNonExpired ;
	private Integer credentialsNonExpired ;
	private Integer accountNonLocked ;
	private Set<UserRole> userRole = new HashSet<UserRole>(0);
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USER_NAME")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name="CREDENTIALS")
	public String getCredentials(){
		return credentials;
	}
	public void setCredentials(String Credentials){
		this.credentials = Credentials;
	}
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	public Set<UserRole> getUserRole() {
		return this.userRole;
	}
 
	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}
	@Column(name="ACCOUNTNONEXPIRED")
	public Integer getAccountNonExpired() {
		return accountNonExpired;
	}
	public void setAccountNonExpired(Integer accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}
	@Column(name="CREDENTIALSNONEXPIRED")
	public Integer getCredentialsNonExpired() {
		return credentialsNonExpired;
	}
	public void setCredentialsNonExpired(Integer credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}
	@Column(name="ACCOUNTNONLOCKED")
	public Integer getAccountNonLocked() {
		return accountNonLocked;
	}
	public void setAccountNonLocked(Integer accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}
	@Override
	@Transient
	public String getComparatorString() {
		return toString();
	}
	@Override
	public String toString() {
		return super.toString() + "#" + getId();
	}
	@Override
	@Column(name ="SECURITY_USER_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id){
		
	}
}
