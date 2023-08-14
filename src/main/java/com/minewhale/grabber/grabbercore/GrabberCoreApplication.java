package com.minewhale.grabber.grabbercore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.minewhale"})
public class GrabberCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrabberCoreApplication.class, args);
    }

}
