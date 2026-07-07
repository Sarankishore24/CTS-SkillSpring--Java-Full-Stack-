package com.employee.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * EX9 - Externalized configuration.
 *
 * Demonstrates reading datasource configuration from application.properties
 * using @Value. In a multi-datasource scenario you would declare @Bean DataSource
 * methods here, each backed by its own set of externalized properties.
 *
 * For this single-H2-datasource exercise, Spring Boot's auto-configuration
 * wires the DataSource automatically using the spring.datasource.* properties,
 * so no explicit @Bean definition is needed. This class shows the pattern.
 */
@Configuration
public class DataSourceConfig {

    /**
     * EX9: Primary datasource URL, read from application.properties.
     * Property: spring.datasource.url
     */
    @Value("${spring.datasource.url}")
    private String primaryUrl;

    /**
     * EX9: Primary datasource username, read from application.properties.
     * Property: spring.datasource.username
     */
    @Value("${spring.datasource.username}")
    private String primaryUsername;

    /**
     * EX9: App-level custom property, read from application.properties.
     * Property: app.datasource.primary.url
     */
    @Value("${app.datasource.primary.url}")
    private String appPrimaryUrl;

    /*
     * Example (not activated here to avoid conflicting with auto-config):
     *
     * @Bean
     * @Primary
     * public DataSource primaryDataSource() {
     *     DriverManagerDataSource ds = new DriverManagerDataSource();
     *     ds.setUrl(primaryUrl);
     *     ds.setUsername(primaryUsername);
     *     ds.setPassword("password");
     *     return ds;
     * }
     */
}
