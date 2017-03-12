package com.lardi.config;

import com.lardi.util.ServiceUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class AuthenticationProviderConfig {

    @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource() throws IOException {
        Properties properties = ServiceUtils.getProperties();
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(properties.getProperty("driverClassName"));
        driverManagerDataSource.setUrl(properties.getProperty("spring.datasource.url"));
        driverManagerDataSource.setUsername(properties.getProperty("spring.datasource.username"));
        driverManagerDataSource.setPassword(properties.getProperty("spring.datasource.password"));
        return driverManagerDataSource;
    }

//    @Profile("mysql")
//    @Bean("dataJpaUserDetailsService")
//    public UserDetailsService userDetailsService() throws IOException {
//        JdbcDaoImpl jdbcImpl = new JdbcDaoImpl();
//        jdbcImpl.setDataSource(dataSource());
//        jdbcImpl.setUsersByUsernameQuery("select login,password,1 from users where login=?");
//        jdbcImpl.setAuthoritiesByUsernameQuery("select b.login, a.role from user_roles a, users b where b.login=? and a.user_id=b.id");
//        return jdbcImpl;
//    }
}

