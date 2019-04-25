package cn.edu.nwpu.ms_srm_exp_manage.service;

import cn.edu.nwpu.ms_srm_exp_manage.domain.SrmExperiment;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ExperimentDataService {
    /**
     * 添加到数据库
     * @param expFile
     */
    void addExperimentData (File expFile, String srmName);

    /**
     * 根据ID删除实验数据
     * @param id
     */
    void deleteExperimentDataById(Integer id);

    /**
     * 根据ID修改数据
     * @param srmExperiment
     */
    void updateExperimentDataById(SrmExperiment srmExperiment);

    /**
     * 根据ID查询单个数据
     * @param id
     * @return
     */
    SrmExperiment findSrmExperimentByPkId(Integer id);

    /**
     * 根据名字查询结果集合
     * @param srmName
     * @return
     */
    SrmExperiment findSrmExperimentBySrmName(String srmName);

    /**
     * 查询所有结果
     * @return
     */
    List<SrmExperiment> findAll();

    /**
     * 对数据的滤波
     * @param id
     * @return
     */
    SrmExperiment smoothData(Integer id) throws IOException;

    /**
     * 将数据存到文件
     * @param path
     * @param id
     */
    void toFile(String path, Integer id);//如何加入文件路径？

}
