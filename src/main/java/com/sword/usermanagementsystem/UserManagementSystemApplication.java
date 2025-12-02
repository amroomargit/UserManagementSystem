package com.sword.usermanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class UserManagementSystemApplication {

	public static void main(String[] args) {

		SpringApplication.run(UserManagementSystemApplication.class, args);

	}

	/*
	- ajax (way of sending page request (as opposed to sending html page refresh)
	- allowing localhost connections between backend port 8080 and angular port 4200
	- The bean makes application capable of accepting any ajax request without browser blocking it due to port difference
*/
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("*")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
						.allowedHeaders("*");
			}
		};
	}

}
