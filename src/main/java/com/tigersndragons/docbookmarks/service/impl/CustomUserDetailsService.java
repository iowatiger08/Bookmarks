package com.tigersndragons.docbookmarks.service.impl;

import com.tigersndragons.docbookmarks.service.LoginService;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	private LoginService loginService;
	
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		return loginService.loadUserByUsername(username);
	}

	@Required
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	
}
