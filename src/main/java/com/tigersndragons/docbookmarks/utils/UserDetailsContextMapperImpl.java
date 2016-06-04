package com.tigersndragons.docbookmarks.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tigersndragons.docbookmarks.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

public class UserDetailsContextMapperImpl implements UserDetailsContextMapper, Serializable{
    private static final long serialVersionUID = 3962976258168853954L;
	@Autowired
	@Qualifier("userDetailsService") 
    private CustomUserDetailsService userService;

    public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authority) {
//    	return userService.loadUserByUsername(username);
        List<GrantedAuthority> mappedAuthorities = new ArrayList<GrantedAuthority>();
        for (GrantedAuthority granted : authority) {

            if (granted.getAuthority().equalsIgnoreCase("APS Team")) {
                mappedAuthorities.add(new GrantedAuthority(){
                    private static final long serialVersionUID = 4356967414267942910L;

                    public String getAuthority() {
                        return "ROLE_USER";
                    } 

                });
            } 
            //not used
//            else if(granted.getAuthority().equalsIgnoreCase("MY ADMIN GROUP")) {
//                mappedAuthorities.add(new GrantedAuthority() {
//                    private static final long serialVersionUID = -5167156646226168080L;
//
//                    public String getAuthority() {
//                        return "ROLE_ADMIN";
//                    }
//                });
//            }
        }
        return new User(username, "", true, true, true, true, mappedAuthorities);
    }

    public void mapUserToContext(UserDetails arg0, DirContextAdapter arg1) {
    	
    }
    
}