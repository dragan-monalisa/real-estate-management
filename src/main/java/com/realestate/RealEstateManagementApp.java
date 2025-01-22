package com.realestate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;

@SpringBootApplication(exclude = LiquibaseAutoConfiguration.class)
public class RealEstateManagementApp {

    public static void main(String[] args) {
        SpringApplication.run(RealEstateManagementApp.class, args);
    }

}
