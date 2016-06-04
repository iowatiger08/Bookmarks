package com.tigersndragons.docbookmarks.security;

import java.util.Enumeration;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

public class EmptyEnumeration<SearchResult> implements NamingEnumeration<SearchResult> {

	 public static Enumeration<?> getInstance()
	    {
	        return enumeration;
	    }
	    
	    /**
	     * @return false;
	     */
	     public boolean hasMoreElements()
	     {
	        return false;
	     }
	     
	    /**
	     * @return null
	     */
	     public SearchResult nextElement()
	     {
	        return null;
	     }
	     
	     protected static Enumeration<?> enumeration = new EmptyEnumeration();

		public SearchResult next() throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		public boolean hasMore() throws NamingException {
			// TODO Auto-generated method stub
			return false;
		}

		public void close() throws NamingException {
			// TODO Auto-generated method stub
			
		}


}
