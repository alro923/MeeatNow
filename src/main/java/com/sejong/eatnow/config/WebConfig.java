package com.sejong.eatnow.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class WebConfig extends WebSecurityConfigurerAdapter {

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //모든 요청에 대해서
                .allowedOrigins("https://localhost:8080/") //허용할 오리진들
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.HEAD.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll();

    }
}