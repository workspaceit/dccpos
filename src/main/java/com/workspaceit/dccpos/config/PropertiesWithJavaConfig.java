package com.workspaceit.dccpos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by anik on 12/18/17.
 */
@Configuration
@PropertySource("classpath:local-config.properties")
//@PropertySource("classpath:live.test.config.properties")
//@PropertySource("classpath:live.config.properties")
public class PropertiesWithJavaConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer
    propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}