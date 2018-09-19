package ru.eosan.camunda.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class Config {

    @Bean
    public FilterRegistrationBean autoLoginAuthenticationFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(autoLoginAuthenticationFilter());
        registration.addUrlPatterns("/*");
        registration.setName("Authentication Filter");
        registration.setOrder(1);
        return registration;
    }

    public Filter autoLoginAuthenticationFilter() {
        return new SSOAuthFilter();
    }
}
