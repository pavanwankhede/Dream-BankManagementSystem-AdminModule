package com.dbms.admin.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ADreamBankManagementSystemAdminModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ADreamBankManagementSystemAdminModuleApplication.class, args);
	}

}
