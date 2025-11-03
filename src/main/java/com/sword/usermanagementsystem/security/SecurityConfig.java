package com.sword.usermanagementsystem.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*This class registers the JwtFilter into Spring Boot's filter chain so that it runs on every incoming HTTP request
* before reaching the controller.*/
@Configuration
public class SecurityConfig {

    @Bean
    /*Registers custom JwtFilter, passing in the existing filter instance as a parameter. The type <JwtFilter> just
    specifies what kind of filter we’re registering.*/
    public FilterRegistrationBean<JwtFilter> jwtFilterFilterRegistration(JwtFilter filter){

        //Creates new registration object that Spring Boot uses to attach filters to web app
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();

        //Attaches JwtFilter to the registration bean. This is what links the JwtFilter class to Spring's web layer
        registrationBean.setFilter(filter);

        //This means that the filter applies to every request path in the application
        registrationBean.addUrlPatterns("/*");

        //returns the configuration to Spring, so we can register the filter during startup
        return registrationBean;
    }
}
