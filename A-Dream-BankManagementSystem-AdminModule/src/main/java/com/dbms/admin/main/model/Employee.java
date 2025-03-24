package com.dbms.admin.main.model;


import com.dbms.admin.main.enums.Designation;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "employeeTable")
@Entity
public class Employee {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeId; // Unique Employee ID

    @NotBlank(message = "Full name cannot be blank")
    @Size(min = 3, max = 20, message = "Full name must be between 3 and 20 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Full name must contain only letters and spaces")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 3, max = 20, message = "Last name must be between 3 and 20 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Last name must contain only letters and spaces")
    private String lastName;

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String userName;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Pattern(
    regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%?&])[A-Za-z\\d@$!%?&]+$",
   message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
    	)
    	private String password;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "^(Male|Female)$", message = "Gender must be Male, Female")
    private String gender;

    @NotNull(message = "Date of birth is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String dateOfBirth;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String emailId;
    
    @Min(value = 0, message = "Salary must be non-negative")
    private double salary;

    @Min(value = 1000000000L, message = "Enter a valid 10-digit contact number")
    @Max(value = 9999999999L, message = "Enter a valid 10-digit contact number")
    private long contactNumber;

    @Size(min = 12, max = 12, message = "Aadhar card must be exactly 12 digits")
    private String aadharCardNo;

    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "PAN card must contain only letters and numbers")
    private String panCardNo;

    @NotBlank(message = "Address cannot be empty")
    private String address;

    @NotBlank(message = "City cannot be empty")
    private String city;

    @NotBlank(message = "State cannot be empty")
    private String state;

    @NotBlank(message = "Country cannot be empty")
    private String country;

    @NotNull(message = "Designation is required")
    @Enumerated(EnumType.STRING)
    private Designation designation;

    @Lob
    private byte[] passportPhoto; // Stores the image as binary data

    
     
    

         

}
