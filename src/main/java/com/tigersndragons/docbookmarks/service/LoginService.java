package com.tigersndragons.docbookmarks.service;

import com.tigersndragons.docbookmarks.exception.LoginNotFoundException;
import com.tigersndragons.docbookmarks.model.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LoginService extends UserDetailsService{
	
	public SecurityUser getSecurityUser(Long id) throws LoginNotFoundException;
	
	public SecurityUser getSecurityUser(String user, String credentials) throws LoginNotFoundException;

	public UserDetails loadUserByUsername(String username, String credentials);

	public SecurityUser updateSecurityUser(UserDetails uDetail, String credentials) throws LoginNotFoundException;
	public SecurityUser getSecurityUser(String user)
			throws LoginNotFoundException;
}
