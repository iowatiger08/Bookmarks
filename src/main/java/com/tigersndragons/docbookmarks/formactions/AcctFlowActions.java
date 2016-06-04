package com.tigersndragons.docbookmarks.formactions;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class AcctFlowActions implements Serializable {

	private static final long serialVersionUID = 1L;
	@NotNull
	private String credentials;
	@NotNull
	private String credentials2;
	private String uName;
	public String getCredentials() {
		return credentials;
	}
	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getCredentials2() {
		return credentials2;
	}
	public void setCredentials2(String credentials2) {
		this.credentials2 = credentials2;
	}
	
}
