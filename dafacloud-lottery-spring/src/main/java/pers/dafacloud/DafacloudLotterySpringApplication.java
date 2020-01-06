package pers.dafacloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import pers.dafacloud.scheduled.Dafa1F5FPreUrl;
import pers.dafacloud.scheduled.Dafa1F5FTestUrl;

@SpringBootApplication
@EnableScheduling
public class DafacloudLotterySpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(DafacloudLotterySpringApplication.class, args);
        //Dafa1F5FTestUrl.init();
        //Dafa1F5FPreUrl.init();
    }

}
