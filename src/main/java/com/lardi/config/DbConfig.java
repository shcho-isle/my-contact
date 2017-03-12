package com.lardi.config;

import com.lardi.util.ServiceUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.IOException;
import java.util.Properties;

@Profile("mysql")
@Configuration
public class DbConfig {

    @Bean
    public DriverManagerDataSource dataSource() throws IOException {
        Properties properties = ServiceUtils.getProperties();
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(properties.getProperty("driverClassName"));
        driverManagerDataSource.setUrl(properties.getProperty("spring.datasource.url"));
        driverManagerDataSource.setUsername(properties.getProperty("spring.datasource.username"));
        driverManagerDataSource.setPassword(properties.getProperty("spring.datasource.password"));
        return driverManagerDataSource;
    }
}

