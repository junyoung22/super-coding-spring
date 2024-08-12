package com.github.supercoding.config;

import com.github.supercoding.properties.DataSourceProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
@RequiredArgsConstructor
public class jdbcConfig {

    private final DataSourceProperties dataSourceProperties;

    @Bean   // 데이터베이스 연결관리
    public DataSource dataSource1(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());
        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        dataSource.setUrl(dataSourceProperties.getUrl());
        return dataSource;
    }

    @Bean   // 데이터베이스 연결관리
    public DataSource dataSource2(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("wnsdud45@@");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/chapter97?useUnicode=true&characterEncoding=UTF-8");
        return dataSource;
    }

    @Bean   // JdbcTemplate 사용가능 / 반복적인 데이터베이스 작업을 줄이고, 예외 처리를 간소화
    public JdbcTemplate jdbcTemplate1() {
        return new JdbcTemplate(dataSource1());
    }
    @Bean   // JdbcTemplate 사용가능 / 반복적인 데이터베이스 작업을 줄이고, 예외 처리를 간소화
    public JdbcTemplate jdbcTemplate2() {
        return new JdbcTemplate(dataSource2());
    }

    @Bean(name = "tm1")   // 트랜잭션 처리 가능함 / 에러시 모두 롤백
    public PlatformTransactionManager transactionManager1() {
        return new DataSourceTransactionManager(dataSource1());
    }
    @Bean(name = "tm2")   // 트랜잭션 처리 가능함 / 에러시 모두 롤백
    public PlatformTransactionManager transactionManager2() {
        return new DataSourceTransactionManager(dataSource2());
    }
}
