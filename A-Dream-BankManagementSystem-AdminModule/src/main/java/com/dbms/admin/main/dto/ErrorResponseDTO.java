package com.dbms.admin.main.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorResponseDTO {
	
	private String message;
    private LocalDateTime timestamp;

    public ErrorResponseDTO(String message, LocalDateTime timestamp) {
        this.message = message;
        this.timestamp = timestamp;
	
}

}