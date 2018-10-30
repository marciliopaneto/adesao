package com.task.adesao.database.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by marcus on 11/10/18.
 */
@Configuration
public class DbConfig {
    @Autowired
    Environment env;

    @Bean
    public JdbcTemplate jdbcTemplate(BasicDataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public BasicDataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        basicDataSource.setUrl(env.getProperty("spring.datasource.url"));
        basicDataSource.setUsername(env.getProperty("spring.datasource.username"));
        basicDataSource.setPassword(env.getProperty("spring.datasource.password"));
        basicDataSource.setDefaultAutoCommit(false);
        return basicDataSource;
    }

}
