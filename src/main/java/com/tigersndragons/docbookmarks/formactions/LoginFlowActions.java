package com.tigersndragons.docbookmarks.formactions;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class LoginFlowActions implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String passcode;
	private Integer count = new Integer(0);
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasscode() {
		return passcode;
	}
	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
}
