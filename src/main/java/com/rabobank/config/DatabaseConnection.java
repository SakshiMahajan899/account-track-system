package com.rabobank.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class DatabaseConnection {
	
    private static DatabaseConnection instance;
    private final HikariDataSource dataSource;

    
    private final DatabaseConfig databaseConfig;

    private DatabaseConnection() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(databaseConfig.getJdbcUrl());
        config.setUsername(databaseConfig.getUsername());
        config.setPassword(databaseConfig.getPassword());
        config.setDriverClassName(databaseConfig.getDriverClassName());
        config.setMaximumPoolSize(databaseConfig.getMaximumPoolSize());
        config.setMinimumIdle(databaseConfig.getMinimumIdle());
        config.setIdleTimeout(databaseConfig.getIdleTimeout());
        config.setPoolName(databaseConfig.getPoolName());
        config.setMaxLifetime(databaseConfig.getMaxLifetime());
        config.setConnectionTimeout(databaseConfig.getConnectionTimeout());

        dataSource = new HikariDataSource(config);
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}