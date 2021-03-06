package frontend.entryPoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"persistence.dao", "business.service", "frontend"})
public class Main {

    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
        System.out.println("Application started");
        System.out.println("Application running");
    }
}
