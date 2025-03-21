package com.dbms.admin.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

@EnableDiscoveryClient
@SpringBootApplication
public class ADreamBankManagementSystemAdminModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ADreamBankManagementSystemAdminModuleApplication.class, args);
	}
   
	@Bean
	public ObjectMapper mapper() {
		
		return new ObjectMapper();
	}
}
