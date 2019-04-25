package cn.edu.nwpu.ms_srm_exp_manage.controller;

import cn.edu.nwpu.ms_srm_exp_manage.domain.SrmExperiment;
import cn.edu.nwpu.ms_srm_exp_manage.repository.SrmExperimentRepository;
import cn.edu.nwpu.ms_srm_exp_manage.service.impl.ExperimentDataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName IndexController
 * @Author: gd
 * @Date: 2019/4/15 09:54
 * @Version: v1.0
 * @Description:
 */
@Controller
public class IndexController {

//    @GetMapping("/show")
//    public String showIndex(){
//        return "list";
//    }
    @Autowired
    SrmExperimentRepository srmExperimentRepository;
    @Autowired
    ExperimentDataServiceImpl experimentDataServiceImpl;
    @GetMapping("/choise")
    public String choise(){
        return "choise";
    }

    @GetMapping("/addData")
    public String add(){
        return "addData";
    }
    @GetMapping("/addDataFailed")
    public String addData(){
        return "addDataFailed";
    }
    @PostMapping("/srmExperiment")
    public ModelAndView add(Model model,MultipartFile expFile, String srmName) throws IOException {
        SrmExperiment srmExperiment = srmExperimentRepository.findBySrmName(srmName);

        model.addAttribute("srmExperiment",srmExperiment);
             if(srmExperiment==null) {
                 File file = new File("D://test1.txt");
                 expFile.transferTo(file);
                 experimentDataServiceImpl.addExperimentData(file, srmName);
//                return true;
                 return new ModelAndView("redirect:/listAll");
             }
//             return false;
        return new ModelAndView("redirect:/addDataFailed");
//        System.out.println("传进来了");
    }
    @GetMapping("/listAll")
    public ModelAndView list(Model model){

        List<SrmExperiment> srmExperimentList = srmExperimentRepository.findAll();
        model.addAttribute("title","已有数据");
        model.addAttribute("srmExperimentList",srmExperimentList);
        return new ModelAndView("listAll","srmModel",model);
    }
    @GetMapping("/chart{pkId}")
    public ModelAndView chart(Model model,@PathVariable("pkId")Integer pkId){

        SrmExperiment srmExperiment = srmExperimentRepository.findSrmExperimentByPkId(pkId);
        model.addAttribute("pkId",pkId);
        return new ModelAndView("chart","srmModel",model);
    }

    public String chart(){
        return "chart";
    }
    @PostMapping("/getChartData")
    public @ResponseBody Map<String,double[]> getChartData(Integer pkId){
        SrmExperiment srmExperiment=  srmExperimentRepository.findSrmExperimentByPkId(pkId);
        String[] expTime = srmExperiment.getExpTime().split(",");
        String[] expPressure = srmExperiment.getExpPressure().split(",");
        String[] expForce = srmExperiment.getExpForce().split(",");
        double[] expTimeD =new double[expTime.length];
        double[] expPressureD =new double[expTime.length];
        double[] expForceD =new double[expTime.length];
        for (int x = 0; x<expForceD.length ;x++){
            expTimeD[x]=Double.parseDouble(expTime[x]);
            expPressureD[x]=Double.parseDouble(expPressure[x]);
            expForceD[x]=Double.parseDouble(expForce[x]);
        }
        Map<String,double[]> srmMap = new HashMap<>();
        srmMap.put("t",expTimeD);
        srmMap.put("p",expPressureD);
        srmMap.put("f",expForceD);
//        model.addAttribute("srmMap",srmMap);
        System.out.println(srmMap.get("t"));
        return srmMap;
    }

    @GetMapping("/findData")
    public String findByName(){
        return "findData";
    }
    @GetMapping("/findByName")
    public String findDataByName(){
        return "findByName";
    }
    @GetMapping("/toFile{pkId}")
    public ModelAndView toFile(Model model,@PathVariable("pkId")Integer pkId){
        model.addAttribute("pkId",pkId);
        return new ModelAndView("toFile","srmModel",model);
    }

    @PostMapping("/findByName")
    public ModelAndView findByName( Model model,String srmName){


//        List<SrmExperiment> srmExperimentList =  srmExperimentRepository.findBySrmName(SrmName);
//        model.addAttribute("title","已有数据");
//        model.addAttribute("srmExperimentList",srmExperimentList);
            String name = "%"+srmName+"%";
        List<SrmExperiment> srmExperimentList = srmExperimentRepository.findBySrmNameLike(name);
//         if(srmExperiment!=null){
//             model.addAttribute("message","true");
//            model.addAttribute("srmExperiment",srmExperiment);
        model.addAttribute("srmExperimentList",srmExperimentList);
//         }
//         else{
//             model.addAttribute("message","false");
//         }
         return new ModelAndView("findedData","srmModel",model);
    }

    @GetMapping("/modify{pkId}")
    public ModelAndView modify(Model model,@PathVariable("pkId")Integer pkId){

        SrmExperiment srmExperiment = srmExperimentRepository.findSrmExperimentByPkId(pkId);
//        model.addAttribute("pkId",pkId);
        model.addAttribute("srmExperiment",srmExperiment);
        return new ModelAndView("modify","srmModel",model);
    }
    @PostMapping("/modifyName")
    public  ModelAndView modifyName(SrmExperiment srmExperiment) {
//        SrmExperiment srmExperiment1 = srmExperimentRepository.findBySrmName(srmExperiment.getSrmName());
        srmExperimentRepository.deleteById(srmExperiment.getPkId());
        SrmExperiment srmExperiment1 = srmExperimentRepository.save(srmExperiment);

//        model.addAttribute("pkId",pkId);
//        model.addAttribute("srmExperiment", new SrmExperiment());
        return new ModelAndView("redirect:/listAll");
    }
//    @GetMapping("/modify")
//    public String modi(){
//        return "modify";
//    }
    @GetMapping("/dataProcess{pkId}")
    public ModelAndView process(Model model,@PathVariable("pkId")Integer pkId){
        model.addAttribute("pkId",pkId);
        return new ModelAndView("dataProcess","srmModel",model);
    }
    @GetMapping("/delete")
    public String delete(){
        return  "deleteData";
    }

    @PostMapping("/deleteById")
    public ModelAndView deleteById(Integer id){
        srmExperimentRepository.deleteById(id);
        return new ModelAndView("redirect:/listAll");
    }

    @GetMapping("/delete{pkId}")
    public ModelAndView deleteByPkId(Model model,@PathVariable("pkId") Integer pkId) {
        srmExperimentRepository.deleteById(pkId);
//        experimentDataServiceImpl.toFile(pkId);
        return new ModelAndView("redirect:/listAll");
    }
    @GetMapping("{pkId}")
    public ModelAndView view(Model model,@PathVariable("pkId") Integer pkId) {

        model.addAttribute("title", "已有数据");
        SrmExperiment srmExperiment=  srmExperimentRepository.findSrmExperimentByPkId(pkId);
        //String expTime = srmExperiment.getExpTime();
        String[] expTime = srmExperiment.getExpTime().split(",");
        String[] expPressure = srmExperiment.getExpPressure().split(",");
        String[] expForce = srmExperiment.getExpForce().split(",");
        String[] eachTimeData = new String[3] ;
        ArrayList<String[]> eachTimeDataList= new ArrayList<String[]>();
        for(int i = 0; i<expTime.length-1;i++){
//            eachTimeData[0]=expTime[i];
//            eachTimeData[1]=expPressure[i];
//            eachTimeData[2]=expForce[i];
            eachTimeDataList.add(new String[]{expTime[i],expPressure[i],expForce[i]});
        }
        model.addAttribute("srmExperiment", srmExperimentRepository.findSrmExperimentByPkId(pkId));
        model.addAttribute("ExpTime",expTime);
      //  model.addAttribute("PkId",pkId);//测试是否接受到数据
        model.addAttribute("ExpPrssure",expPressure);
        model.addAttribute("ExpForce",expForce);
        model.addAttribute("eachTimeDataList",eachTimeDataList);
        return new ModelAndView("/dataOne", "srmModel", model);
    }

    @PostMapping("/smoothData")
    public @ResponseBody Map<String,double[]> smoothData(Integer pkId){
        SrmExperiment srmExperiment = experimentDataServiceImpl.smoothData(pkId);
        //获取滤波后的实体对象，后面的操作与上面的getChartData函数相同
        String[] expTime = srmExperiment.getExpTime().split(",");
        String[] expPressure = srmExperiment.getExpPressure().split(",");
        String[] expForce = srmExperiment.getExpForce().split(",");
        //字符串数组转化为double数组
        double[] expTimeD =new double[expTime.length];
        double[] expPressureD =new double[expTime.length];
        double[] expForceD =new double[expTime.length];
        for (int x = 0; x<expForceD.length ;x++){
            expTimeD[x]=Double.parseDouble(expTime[x]);
            expPressureD[x]=Double.parseDouble(expPressure[x]);
            expForceD[x]=Double.parseDouble(expForce[x]);
        }
        Map<String,double[]> srmMap = new HashMap<>();
        srmMap.put("t",expTimeD);
        srmMap.put("p",expPressureD);
        srmMap.put("f",expForceD);
        System.out.println(srmMap.get("p"));
        return srmMap;

    }
    @GetMapping("/getChart")
    public String getChart(){
        return "chart";
    }

    @PostMapping("/saveAsFile")
    public @ResponseBody String saveToFile(String path, String pkId,Model model){
        experimentDataServiceImpl.toFile(path,Integer.parseInt(pkId));
        String str = path;
//        Map<String,String> map = new HashMap<>();
//        map.put("path",path);
        model.addAttribute("path",path);
        return path;
    }
    @GetMapping("example")
    public String exa(){
        return "example";
    }
//    class DataFile{
//        DataFile DataFile(){
//            return DataFile();
//        }
//        String path;
//        String pkId;
//    }
    @GetMapping("/frame")
    public String frame(){
        return "frame";
    }
}

