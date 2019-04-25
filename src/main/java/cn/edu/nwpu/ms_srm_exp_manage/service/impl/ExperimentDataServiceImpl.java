package cn.edu.nwpu.ms_srm_exp_manage.service.impl;

import cn.edu.nwpu.ms_srm_exp_manage.domain.SrmExperiment;
import cn.edu.nwpu.ms_srm_exp_manage.repository.SrmExperimentRepository;
import cn.edu.nwpu.ms_srm_exp_manage.service.ExperimentDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName ExperimentDataServiceImpl
 * @Author: gd
 * @Date: 2019/4/14 10:54
 * @Version: v1.0
 * @Description:
 */
@Service
public class ExperimentDataServiceImpl implements ExperimentDataService {
    @Autowired
    SrmExperimentRepository srmExperimentRepository;

    @Override
    public void addExperimentData(File expFile, String srmName) {
        SrmExperiment srmExperiment = new SrmExperiment();
        String exp_time;
        String exp_pressure;
        String exp_force;

        try {
            BufferedReader br = new BufferedReader(new FileReader(expFile));
            exp_time = "";
            exp_pressure = "";
            exp_force = "";
            String line = br.readLine();
            while ((line = br.readLine())!=null) {
                String[] str = line.split("\\s+");
                exp_time += str[0];
                exp_time += ",";
                exp_pressure += str[1];
                exp_pressure += ",";
                exp_force += str[2];
                exp_force += ",";
            }
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        exp_time = exp_time.substring(0,exp_time.length()-1);
        exp_pressure= exp_pressure.substring(0,exp_pressure.length()-1);
        exp_force = exp_force.substring(0,exp_force.length()-1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        Date gmtCreate = new Date();
//        Date lastModifiedTime = new Date(expFile.lastModified());
//        String gmtCreate =sdf.format(date);//获得当前时间
//        String dateString = sdf.format(expFile.lastModified());//获得文件修改时间
        srmExperiment.setExpForce(exp_force);
        srmExperiment.setExpPressure((exp_pressure));
        srmExperiment.setExpTime(exp_time);
        srmExperiment.setSrmName(srmName);
        srmExperiment.setGmtCreate(gmtCreate);
        System.out.println(gmtCreate);

        srmExperimentRepository.save(srmExperiment);
    }

    @Override
    public void deleteExperimentDataById(Integer id) {
        srmExperimentRepository.deleteById(id);
        System.out.println("service方法：deleteExperimentDataById()");
    }

    @Override
    public void updateExperimentDataById(SrmExperiment srmExperiment) {
        SrmExperiment oldSrmExperient = findSrmExperimentBySrmName(srmExperiment.getSrmName());
        srmExperiment.setPkId(oldSrmExperient.getPkId());
        srmExperimentRepository.save(srmExperiment);
    }

    @Override
    public SrmExperiment findSrmExperimentByPkId(Integer id) {
        return srmExperimentRepository.findSrmExperimentByPkId(id);
    }

    @Override
    public SrmExperiment findSrmExperimentBySrmName(String srmName) {
        return srmExperimentRepository.findBySrmName(srmName);
    }
    @Override
    public List<SrmExperiment> findAll(){
        return srmExperimentRepository.findAll();
    }
    @Override
    public  SrmExperiment smoothData(Integer id)  {
        SrmExperiment srmExperiment = srmExperimentRepository.findSrmExperimentByPkId(id);
        String[] tString = srmExperiment.getExpTime().split(",");
        String[] fString = srmExperiment.getExpForce().split(",");
        String[] pString = srmExperiment.getExpPressure().split(",");
        ArrayList<Double> tList = new ArrayList<>();
        ArrayList<Double> fList = new ArrayList<>();
        ArrayList<Double> pList = new ArrayList<>();

//        Map<String, ArrayList<Double>> map = new HashMap<>();
//        map.put("t", tList);
//        map.put("p", (ArrayList<Double>) pList);
//        ArrayList<Double> mlist = map.get("p");
        int len = tString.length;
        int n=30;
        for(int x = 0; x<len; x++){
            if (x<n){
                tList.add(Double.parseDouble(tString[x]));
                fList.add(Double.parseDouble(fString[x]));
                pList.add(Double.parseDouble(pString[x]));
//                rk2.setRp(Alr.get(x).getRp());
//                Alr2.add(rk2);
            }
            else{
                tList.add(Double.parseDouble(tString[x]));
//                rk2.setT(Alr.get(x).getT());
                double fsum = 0.0,psum = 0.0;
                for(int y =0; y<n; y++){
                    fsum+=Double.parseDouble(fString[x-y]);
                    psum+=Double.parseDouble(pString[x-y]);
                }
                double fave = fsum/n;
                double pave = fsum/n;
                fList.add(Double.parseDouble(fString[x]));
                pList.add(Double.parseDouble(pString[x]));
            }

        }
        srmExperiment.setExpTime(tList.toString());
        srmExperiment.setExpPressure(pList.toString());
        srmExperiment.setExpForce(fList.toString());
        System.out.println(fList.toString());
        return srmExperiment;
    }
    @Override
    public void toFile(String path ,Integer id){
        SrmExperiment srmExperiment = srmExperimentRepository.findSrmExperimentByPkId(id);
        String[] tString = srmExperiment.getExpTime().split(",");
        String[] fString = srmExperiment.getExpForce().split(",");
        String[] pString = srmExperiment.getExpPressure().split(",");

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            bw.write(" t(s) ");
            bw.write(',');
            bw.write(" Pb(Mpa) ");
            bw.write(',');
            bw.write(" F(kN) ");
            bw.newLine();
            for (int x = 0; x<tString.length; x++) {
                bw.write(tString[x]);
                bw.write(',');
                bw.write(pString[x]);
                bw.write(',');
                bw.write(fString[x]);
                bw.newLine();
            }
            bw.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
