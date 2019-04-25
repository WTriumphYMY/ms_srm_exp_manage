package cn.edu.nwpu.ms_srm_exp_manage.service.impl;

import cn.edu.nwpu.ms_srm_exp_manage.service.ExperimentDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExperimentDataServiceImplTest {

    @Autowired
    ExperimentDataService experimentDataService;
    @Test
    public void addExperimentData() {
        File file = new File("D:\\bs\\testgd.txt");
        String srmName = "srm_001";
        experimentDataService.addExperimentData(file,srmName);
        System.out.println("方法：addExperimentData()");
    }

    @Test
    public void deleteExperimentDataById() {
        Integer id = 1 ;
        experimentDataService.deleteExperimentDataById(id);
        System.out.println("方法：deleteExperimentDataById()");
    }

    @Test
    public void updateExperimentDataById() {
    }

    @Test
    public void findSrmExperimentByPkId() {
    }

    @Test
    public void findSrmExperimentBySrmName() {
    }
}