package cn.edu.nwpu.ms_srm_exp_manage;

import cn.edu.nwpu.ms_srm_exp_manage.service.ExperimentDataService;
import cn.edu.nwpu.ms_srm_exp_manage.service.impl.ExperimentDataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class MsSrmExpManageApplication {
//    @Autowired
//    ExperimentDataService experimentDataService ;
    public static void main(String[] args) {
        SpringApplication.run(MsSrmExpManageApplication.class, args);

    }

}
