package pers.dafacloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DafacloudWebApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DafacloudWebApplication.class);
        app.run(args);
    }

}
