package com.dbms.admin.main.employeeCredentials;

import org.apache.commons.lang.RandomStringUtils;

public class UsernamePasswordGenerator {
	
	public static String generateUsername(String firstName) {
	    return Character.toUpperCase(firstName.charAt(0)) + 
	           firstName.substring(1).toLowerCase() + 
	           RandomStringUtils.randomNumeric(3);
	}
	
	public static String generatePassword(String firstName) {
	    return Character.toUpperCase(firstName.charAt(0)) + 
	           firstName.substring(1, Math.min(4, firstName.length())).toLowerCase() + // Ensure at least 2 lowercase letters
	           RandomStringUtils.random(1, "@") + // 1 special character
	           RandomStringUtils.randomNumeric(3); // 3 digits
	}
}