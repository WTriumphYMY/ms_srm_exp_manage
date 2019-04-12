package cn.edu.nwpu.ms_srm_exp_manage.repository;

import cn.edu.nwpu.ms_srm_exp_manage.domain.SrmExperiment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @InterfaceName SrmExperimentRepository
 * @Author: wkx
 * @Date: 2019/4/12 20:19
 * @Version: v1.0
 * @Description: SrmExperiment持久化类
 */
public interface SrmExperimentRepository extends JpaRepository<SrmExperiment, Integer> {
}
