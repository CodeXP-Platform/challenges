package com.codexp.challenges;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ChallengesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChallengesApplication.class, args);
    }

}
