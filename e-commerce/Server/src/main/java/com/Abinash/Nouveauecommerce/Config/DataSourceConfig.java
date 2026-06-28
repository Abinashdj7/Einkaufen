package com.Abinash.Nouveauecommerce.Config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean(name = "primaryDataSource")
    public DataSource primaryDataSource(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password,
            @Value("${spring.datasource.driver-class-name:}") String driverClassName) {
        var b = DataSourceBuilder.create().url(url).username(username).password(password);
        if (!driverClassName.isBlank()) b.driverClassName(driverClassName);
        return b.build();
    }

    @Bean(name = "replicaDataSource")
    public DataSource replicaDataSource(
            @Value("${spring.datasource.replica.url:${spring.datasource.url}}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password,
            @Value("${spring.datasource.driver-class-name:}") String driverClassName) {
        var b = DataSourceBuilder.create().url(url).username(username).password(password);
        if (!driverClassName.isBlank()) b.driverClassName(driverClassName);
        return b.build();
    }

    @Primary
    @Bean(name = "dataSource")
    public DataSource routingDataSource(
            @Qualifier("primaryDataSource") DataSource primary,
            @Qualifier("replicaDataSource") DataSource replica) {
        var routing = new ReplicationRoutingDataSource();
        routing.setTargetDataSources(Map.of("primary", primary, "replica", replica));
        routing.setDefaultTargetDataSource(primary);
        routing.afterPropertiesSet();
        // LazyConnectionDataSourceProxy ensures the read-only flag is set on the
        // transaction context before a connection is acquired, so routing works correctly.
        return new LazyConnectionDataSourceProxy(routing);
    }
}
