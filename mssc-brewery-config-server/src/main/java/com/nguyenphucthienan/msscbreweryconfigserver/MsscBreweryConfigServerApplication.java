package com.nguyenphucthienan.msscbreweryconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class MsscBreweryConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsscBreweryConfigServerApplication.class, args);
    }

}
