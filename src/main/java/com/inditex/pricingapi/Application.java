package com.inditex.pricingapi;

import com.inditex.pricingapi.config.EnvProfile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        String[] profile = EnvProfile.setupProfile();

        SpringApplication app = new SpringApplication(Application.class);
        app.setAdditionalProfiles(profile);
        app.run(args);
    }
}
