package com.example.microservice.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info= @Info(
				title="Accounts Microservice Rest Api Documentation",
				description="Spring boot accounts microservice Rest API Document",
				version="v1",
				contact=@Contact(name="Ramya Karanam",email="ramya123@gmail.com",url="https://www.java.com"),
				license=@License(name="Apache 2.0",url="https://java.com")
				),
		externalDocs= @ExternalDocumentation(
				description="Spring Boot microservice API Rest Documentation",
				url="https://www.java.com"
				)
		)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
