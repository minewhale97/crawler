package com.minewhale.grabber.grabbercore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.minewhale"})
@ServletComponentScan(basePackages = "com.minewhale.grabber.grabbercore.web")
public class GrabberCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrabberCoreApplication.class, args);
    }

}
