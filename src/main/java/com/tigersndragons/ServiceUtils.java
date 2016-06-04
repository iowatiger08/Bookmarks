package com.tigersndragons;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;

public class ServiceUtils {
	public static void assertNotNull(String message, Object value){
		if(value == null){
			throw new IllegalArgumentException(message);
		}
	}
	
}
