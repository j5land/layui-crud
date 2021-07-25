package com.j5land.layuicrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class,scanBasePackages = "com.j5land")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
