package com.dbms.admin.main.serviceinterface;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailDetails {
	
	   private static final Logger log = LoggerFactory.getLogger(EmailDetails.class);
	    
	    @Autowired
	    private JavaMailSender mailSender;
	    
	    @Value("${spring.mail.username}")
	    private String FROM_MAIL;
	    public void sendEmail(String toEmail, String userName, String password) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(toEmail);
	        message.setSubject("Your Account Activation - Important Instructions");
	        message.setText("Dear Employee,\n\nYour account has been created successfully.\n\n"
	                        + "Your-Username: " + userName + "\n"
	                        + "Your-Password: " + password + "\n\n"
	                        + "For your security, please do not share this email.\n" 
	                        + "Please change your password after logging in.\n\n"
	                        + "Best Regards,\n "
	                        + "Your Bank Support Team");

	        mailSender.send(message);
	        log.info("Email sent successfully to " + toEmail);
	    }
}

