package com.tigersndragons.docbookmarks.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tigersndragons.docbookmarks.dao.DocBookmarksDAO;
import com.tigersndragons.docbookmarks.exception.LoginNotFoundException;
import com.tigersndragons.docbookmarks.model.SecurityUser;
import com.tigersndragons.docbookmarks.model.UserAttempts;
import com.tigersndragons.docbookmarks.model.UserRole;
import com.tigersndragons.docbookmarks.service.LoginService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginServiceImpl  extends DaoAuthenticationProvider implements LoginService {
	
	private DocBookmarksDAO dbDAO;
	@Autowired
	@Qualifier("activeDirectoryLdapAuthenticationProvider")
	ActiveDirectoryLdapAuthenticationProvider activeDirectoryLdapAuthenticationProvider;
	
	public SecurityUser getSecurityUser(Long id) throws LoginNotFoundException {
		List<SecurityUser> alist= dbDAO.findByKeyValue(SecurityUser.class, "id", id);
		if (alist.isEmpty() || alist.size()>1){
			return null;
		}else{
			return alist.get(0);
		}
	}

	public SecurityUser getSecurityUser(String user, String credentials)
			throws LoginNotFoundException,BadCredentialsException {
		SecurityUser su = getSecurityUser( user);
		UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(user, credentials);
		if (su!= null ){
			Authentication result = activeDirectoryLdapAuthenticationProvider.authenticate(upToken);
			if (result.isAuthenticated())
				return su;
		}
		throw new LoginNotFoundException(user);
	}
	
	public SecurityUser getSecurityUser(String user)
			throws LoginNotFoundException {
		List<SecurityUser> su = dbDAO.findByKeyValue(SecurityUser.class, "userName", user);
		if (su.isEmpty() || su.size()>1)
				throw new LoginNotFoundException(user);
		else{
			return su.get(0);
		}
	}

	@Transactional(readOnly=true)
	//@Override
	public UserDetails loadUserByUsername(final String username, final String credentials) {
 
		SecurityUser user = null;
		try {
			user = getSecurityUser(username, credentials);
		} catch (LoginNotFoundException e) {
			return null;
		}
		List<GrantedAuthority> authorities = 
                                      buildUserAuthority(user.getUserRole());
 
		return buildUserForAuthentication(user, authorities);
 
	}
	
	// Converts SecurityUser user to
	// org.springframework.security.core.userdetails.User
	private org.springframework.security.core.userdetails.User buildUserForAuthentication(SecurityUser user, 
		List<GrantedAuthority> authorities) {
		return new User(user.getUserName(), user.getCredentials(), 
			true, user.getAccountNonExpired().intValue()==1, 
			user.getCredentialsNonExpired().intValue()==1, 
			user.getAccountNonLocked().intValue()==1, 
			authorities);
	}
 
	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
 
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
 
		// Build user's authorities
		for (UserRole userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}
 
		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
		return Result;
	}
	
	public void updateFailAttempts(String username) throws LoginNotFoundException {
		UserAttempts attempts = getUserAttempts(username);
		attempts.setLastModified(new DateTime());
		int cnt =attempts.getAttempts().intValue();
		attempts.setAttempts(new Integer(cnt++));
		dbDAO.save(attempts);		
	}
	
	public UserAttempts getUserAttempts(String user) throws LoginNotFoundException {
		List<UserAttempts> su = dbDAO.findByKeyValue(UserAttempts.class, "userName", user);
		if (su.isEmpty())
				throw new LoginNotFoundException(user);
		else{
			return su.get(0);
		}
		
	}
	
	public void resetFailAttempts(String username) throws LoginNotFoundException {
		UserAttempts attempts = getUserAttempts(username);
		attempts.setLastModified(new DateTime());
		attempts.setAttempts(new Integer(0));
		dbDAO.save(attempts);
	}
	
	
	@Override
	public Authentication authenticate(Authentication authentication) 
          throws AuthenticationException {
 
		try {
			try {

				//Authentication auth = super.authenticate(authentication);
				resetFailAttempts(authentication.getName());
				return authentication;

			} catch (BadCredentialsException e) {
				updateFailAttempts(authentication.getName());
				throw e;

			} catch (LockedException e) {
				String error = "";
				UserAttempts userAttempts = getUserAttempts(authentication
						.getName());

				if (userAttempts != null) {
					DateTime lastAttempts = userAttempts.getLastModified();
					error = "Sorry Locked! "
//							+ "<br/><br/>Username : "
//							+ authentication.getName() 
							+ "| Last Attempts : "
							+ lastAttempts;
				} else {
					error = e.getMessage();
				}

				throw new LockedException(error);
			}
		} catch (LoginNotFoundException nf) {
			throw new LockedException(nf.getMessage());
		}
 
	}
	
	@Autowired
	@Qualifier("userDetailsService")
	@Override
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		super.setUserDetailsService(userDetailsService);
	}
	@Required
	public void setDbDAO(DocBookmarksDAO dbDAO) {
		this.dbDAO = dbDAO;
	}

	public SecurityUser updateSecurityUser(UserDetails uDetail, String credentials) throws LoginNotFoundException {
		SecurityUser su = getSecurityUser(uDetail.getUsername());
		su.setCredentials(credentials);
		dbDAO.save(su);
		return su;
	}

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		SecurityUser user = null;
		try {
			user = getSecurityUser(username);
		} catch (LoginNotFoundException e) {
			return null;
		}
		List<GrantedAuthority> authorities = 
                                      buildUserAuthority(user.getUserRole());
 
		return buildUserForAuthentication(user, authorities);
	}
	
}
