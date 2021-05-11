package com.endeymus.parser.config;

import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.sql.DataSource;

/**
 * @author Mark Shamray
 */
@Configuration
@ComponentScan(value = "com.endeymus.parser")
@Import(DataBaseConfig.class)
@EnableJpaRepositories("com.endeymus.parser.repository")
@EnableScheduling
public class AppConfig {

    @Bean
    public String newLine() {
        return System.lineSeparator();
    }


}
