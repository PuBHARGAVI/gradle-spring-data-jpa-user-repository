package com.hxs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *   Sample CRUD User Application
 *
 *   Available Profiles:
 *    - rdbms: Uses Database (Currently Postgres)
 *    - dev: Enables dev mode -> i.e. Swagger api-docs runtime generation
 *
 *   TODO: fill out controller interfaces with Swagger annotations
 */
@SpringBootApplication
public class UserCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCrudApplication.class, args);
    }

}