package com.tigersndragons.docbookmarks.service;

import static org.junit.Assert.assertNotNull;

import com.tigersndragons.docbookmarks.exception.LoginNotFoundException;
import com.tigersndragons.docbookmarks.BaseTestCase;
import com.tigersndragons.docbookmarks.model.SecurityUser;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginServiceTest extends BaseTestCase {
/*	
  		private static final String SELECT_DOC_SHELL_1 = 
			"SELECT * FROM DOC_SHELL order by DOC_SHELL_ID asc";

		private static final String SELECT_DOC_SHELL_2 = 
			"SELECT * FROM DOC_SHELL WHERE DOC_SHELL_ID = 606";
*/
		@Autowired
		LoginService loginService;
		@Ignore //not used anymore
		@Test
		public void loadDocShellRecords(){
			SecurityUser su = null;
			try {
				su = loginService.getSecurityUser(1L);
			} catch (LoginNotFoundException e) {
				assert(su!=null);
			}
			assertNotNull  (su);
			assert (!su.getId().equals(1L) && su.getUserName().equalsIgnoreCase("tdillon"));
			//printResults(alist);
		}
		
		@Test
		public void loginServiceUsername(){
			SecurityUser su = null;
			try {
				su = loginService.getSecurityUser("tdillon", "Password1");//.getDocShellById(id);
			} catch (LoginNotFoundException e) {
				assert(su!=null);
			}
			assertNotNull  (su);
			assert (!su.getId().equals(1L) && su.getUserName().equalsIgnoreCase("tdillon"));
			
		}
		
		@Test
		(expected =LoginNotFoundException.class)
		public void loginIncorrect() throws LoginNotFoundException{
			SecurityUser su = null;			
			su = loginService.getSecurityUser("admin", "password");//.getDocShellById(id);			
		}
}
