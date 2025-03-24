package com.dbms.admin.main.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponseDTO {
    private String message;
    private LocalDateTime timestamp;
    
}
