package cn.edu.nwpu.ms_srm_exp_manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsSrmExpManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsSrmExpManageApplication.class, args);

    }

}
