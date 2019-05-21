package cn.edu.nwpu.ms_srm_exp_manage.controller;

import cn.edu.nwpu.ms_srm_exp_manage.domain.SrmExperiment;
import cn.edu.nwpu.ms_srm_exp_manage.repository.SrmExperimentRepository;
import cn.edu.nwpu.ms_srm_exp_manage.service.ExperimentDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName IndexController
 * @Author: wkx
 * @Date: 2019/5/5 21.31
 * @Version: v2.0
 * @Description: 实验数据管理第二版
 */
@Controller
public class IndexController {

    @Autowired
    SrmExperimentRepository srmExperimentRepository;
    @Autowired
    ExperimentDataService experimentDataService;

    @GetMapping("/index")
    public String newIndex(Model model) {
        model.addAttribute("expData", srmExperimentRepository.findAll());
        return "newindex";
    }

    @PostMapping("/findAllExp")
    @ResponseBody
    public List<SrmExperiment> findAll(){
        return srmExperimentRepository.findAll();
    }

    @PostMapping("/addOrUpdate")
    public ModelAndView add(MultipartFile expFile, String srmName) throws IOException {

        File file = new File("D://test1.txt");
        expFile.transferTo(file);
        experimentDataService.addOrUpdateSrmExperiment(file, srmName);
        return new ModelAndView("redirect:/index");
    }

    @GetMapping("/getSrmExpById/{pkId}")
    public String findById(@PathVariable Integer pkId, Model model) {
        SrmExperiment srmExperiment = experimentDataService.findSrmExperimentByPkId(pkId);
        model.addAttribute("expdata", srmExperiment);
        return "detail";
    }

    @PostMapping("/getExpByName")
    @ResponseBody
    public SrmExperiment findBySrmName(@RequestParam String srmName) {
        SrmExperiment srmExperiment = srmExperimentRepository.findBySrmName(srmName);

        return srmExperiment;
    }

    @GetMapping("/download/{pkId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Integer pkId) throws IOException {
        SrmExperiment srmExperiment = experimentDataService.findSrmExperimentByPkId(pkId);
        File file = new File("D://" + srmExperiment.getSrmName() + ".txt");
        BufferedWriter bw = Files.newBufferedWriter(file.toPath());
        bw.write("t(s)\tPb(Mpa)\tF(kN)");
        bw.newLine();
        String[] tdata = srmExperiment.getExpTime().split(",");
        String[] pdata = srmExperiment.getExpPressure().split(",");
        String[] fdata = srmExperiment.getExpForce().split(",");
        for (int i = 0; i < srmExperiment.getExpTime().split(",").length; i++) {
            bw.write(tdata[i] + "\t" + pdata[i] + "\t" + fdata[i]);
            bw.newLine();
        }
        bw.close();
        byte[] body;
        InputStream in = new FileInputStream(file);
        body = new byte[in.available()];
        in.read(body);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename=" + file.getName());

        HttpStatus statusCode = HttpStatus.OK;

        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, statusCode);
        return response;
    }

    @GetMapping("/deleteById/{pkId}")
    public ModelAndView deleteById(@PathVariable("pkId") Integer pkId) {
        srmExperimentRepository.deleteById(pkId);
        return new ModelAndView("redirect:/index");
    }

    @PostMapping("/findAllSrmName")
    @ResponseBody
    public List<String> findAllSrmName(){
        return srmExperimentRepository.findAll().stream().map(SrmExperiment :: getSrmName).collect(Collectors.toList());
    }

}


