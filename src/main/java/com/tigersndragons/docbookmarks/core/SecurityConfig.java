package com.tigersndragons.docbookmarks.core;

import com.tigersndragons.docbookmarks.service.LoginService;
import com.tigersndragons.docbookmarks.utils.UserDetailsContextMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.event.LoggerListener;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
	@Autowired
	@Qualifier("loginService")
	LoginService loginService;
	private final String LDAP_URL="ipers09.ipers.local";
 
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(activeDirectoryLdapAuthenticationProvider());
		auth.userDetailsService(loginService).passwordEncoder(passwordEncoder());
	}
 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.authorizeRequests().antMatchers("/**")
		.access("hasRole('ROLE_USER')").and().formLogin()
		.loginPage("/login").failureUrl("/login?error")
		.usernameParameter("username")
		.passwordParameter("passcode")
		.and().logout().logoutSuccessUrl("/login?logout")
		.and().csrf()
		.and().exceptionHandling().accessDeniedPage("/403");
	    http.authenticationProvider(activeDirectoryLdapAuthenticationProvider());
	}


	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

	@Bean
	public ActiveDirectoryLdapAuthenticationProvider activeDirectoryLdapAuthenticationProvider(){
		ActiveDirectoryLdapAuthenticationProvider provider = new ActiveDirectoryLdapAuthenticationProvider("ipers.local", "ldap://"+LDAP_URL+":389");
		provider.setUseAuthenticationRequestCredentials(true);
		provider.setUserDetailsContextMapper(userDetailMapper());
		provider.setConvertSubErrorCodesToExceptions(true);
	    provider.setUseAuthenticationRequestCredentials(true);
	    return provider;
	}

    @Bean
    public LoggerListener loggerListener() {
        return new LoggerListener();
    }
    @Bean 
    public UserDetailsContextMapper userDetailMapper(){
    	UserDetailsContextMapperImpl mapper = new UserDetailsContextMapperImpl();
		return mapper;   	
    }
    
    //------------ below not needed if able to use AD authenticator
	
	@Bean
	public LdapContextSource contextSource(){
		LdapContextSource context = new LdapContextSource();
		context.setUrl("ldap://"+LDAP_URL+":389");
		context.setBase("DC=ipers,DC=local");
		context.setUserDn("sAMAccountName=username");
		context.setPassword("password");
		return context;
	}
	@Bean
	public LdapTemplate ldapTemplate(){
		LdapTemplate template = new LdapTemplate(contextSource());
		return template;				
	}
	@Bean 
	public BindAuthenticator bindAuthenticator(){
		BindAuthenticator auth = new BindAuthenticator(contextSource());
		auth.setUserDnPatterns(new String []{"<list><value>uid={0}","ou=people</value>"});
		return auth;
	}
	
	@Bean 
	public DefaultLdapAuthoritiesPopulator defaultLdapAuthoritiesPopulator(){
		DefaultLdapAuthoritiesPopulator defaultLdapAuthoritiesPopulator= new DefaultLdapAuthoritiesPopulator(contextSource(), "ou=groups");
		defaultLdapAuthoritiesPopulator.setGroupRoleAttribute("ou");
		return defaultLdapAuthoritiesPopulator;
		
	}
	@Bean
	public LdapAuthenticationProvider ldapAuthProvider(){
		LdapAuthenticationProvider provider = new LdapAuthenticationProvider(bindAuthenticator(), defaultLdapAuthoritiesPopulator());
		return provider;
	}
}
