package pers.dafacloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DafacloudLotterySpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(DafacloudLotterySpringApplication.class, args);
    }

}
