package com.selfgrowth.incomeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EntityScan("com.selfgrowth.domain")
@EnableConfigurationProperties({LiquibaseProperties.class})
public class IncomeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IncomeServiceApplication.class, args);
    }

}
