package com.sv.clinica.uca.clinica_uca.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringDocConfigurations {

	@Bean
	 public OpenAPI customOpenAPI() {
	   return new OpenAPI()
	          .components(new Components()
	          .addSecuritySchemes("bearer-key",
	        		  new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
	          .info(new Info()
	        		  .title("API Clinica UCA")
	        		  .description("API Rest de la Clinica UCA, que contiene las funcionalidades de CRUD de médicos y pacientes, así como programación y cancelación de consultas.")
	        		  .contact(new Contact()
	        				  .name("Equipo Backend")
	        				  .email("backend@uca.edu.sv"))
	        		  .license(new License()
	        				  .name("Apache 2.0")
	        				  .url("http://clinica.uca/api/licencia")));
	}
	
	@Bean
	public void message() {
		System.out.println("Bearer is working");
	}
}
