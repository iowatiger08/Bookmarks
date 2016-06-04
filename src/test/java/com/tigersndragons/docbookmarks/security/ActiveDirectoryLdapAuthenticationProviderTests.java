package com.tigersndragons.docbookmarks.security;

import javax.naming.Name;
import javax.naming.NameNotFoundException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import com.tigersndragons.docbookmarks.BaseTestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;

public class ActiveDirectoryLdapAuthenticationProviderTests extends BaseTestCase {
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	private final String LDAP_URL="ipers09.ipers.local";

    ActiveDirectoryLdapAuthenticationProvider provider;
    UsernamePasswordAuthenticationToken joe = new UsernamePasswordAuthenticationToken("joe", "password");
    
    @Before
    public void setUp() throws Exception {
        provider = new ActiveDirectoryLdapAuthenticationProvider("ipers.local", "ldap://"+LDAP_URL+":389");
    }
    
    @Test(expected = BadCredentialsException.class)
    public void noUserSearchCausesUsernameNotFound() throws Exception {
        DirContext ctx = mock(DirContext.class);
        when(ctx.getNameInNamespace()).thenReturn("");
        when(ctx.search(any(Name.class), any(String.class), any(Object[].class), any(SearchControls.class)))
        .thenThrow(new NameNotFoundException());

        provider.authenticate(joe);
    }    

    
    @Test
    public void successfulAuthenticationProducesExpectedAuthorities() throws Exception {
        UsernamePasswordAuthenticationToken tdillon = new UsernamePasswordAuthenticationToken("tdillon", "log!tech80");

        DirContext ctx = mock(DirContext.class);
        when(ctx.getNameInNamespace()).thenReturn("");

        DirContextAdapter dca = new DirContextAdapter();
        SearchResult sr = new SearchResult("CN=Tony Dillon-Hansen,CN=Users", dca, dca.getAttributes());

        Authentication result = provider.authenticate(tdillon);

        assertEquals(9, result.getAuthorities().size());

    }
}
