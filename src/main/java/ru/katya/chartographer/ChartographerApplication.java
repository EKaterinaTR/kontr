package ru.katya.chartographer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class ChartographerApplication {

    public static void main(String[] args) {
        SpringApplication sa = new SpringApplication(ChartographerApplication.class);
        Properties properties = new Properties();
        properties.setProperty("storage", args[0]);
        sa.setDefaultProperties(properties);
        sa.run(args);
    }

}
