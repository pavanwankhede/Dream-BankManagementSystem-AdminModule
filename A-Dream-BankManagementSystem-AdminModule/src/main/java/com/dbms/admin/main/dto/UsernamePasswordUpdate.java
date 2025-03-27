package com.dbms.admin.main.dto;

import lombok.Data;

@Data
public class UsernamePasswordUpdate {
	
	    private String oldUsername;
	    private String newUsername;
	    private String oldPassword;
	    private String newPassword;

}
