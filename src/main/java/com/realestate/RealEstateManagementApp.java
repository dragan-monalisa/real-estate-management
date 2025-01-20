package com.realestate;

import com.realestate.config.properties.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(CorsProperties.class)
@SpringBootApplication
public class RealEstateManagementApp {

    public static void main(String[] args) {
        SpringApplication.run(RealEstateManagementApp.class, args);
    }

}
