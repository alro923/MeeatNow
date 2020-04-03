package com.sejong.eatnow.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile(value = "develop-mysql")
@PropertySource({"classpath:application.properties"})
public class MysqlProfile {
}