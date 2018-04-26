package com.chengdu.jiq.service.jdbc.service;

import com.chengdu.jiq.common.annotation.DbSwitch;
import com.chengdu.jiq.model.bo.CityModel;
import com.chengdu.jiq.service.jdbc.repository.CityReposiroty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jiyiqin on 2017/11/5.
 */
@DbSwitch
@Service
public class CityService {

    @Autowired
    private CityReposiroty cityReposiroty;

    /**
     * 底层调用JdbcTemplate，走DatasourceSingConfig配置的jdbc单一数据源
     *
     * @return
     */
    public List<CityModel> listByJdbcTemplate() {
        return cityReposiroty.listByJdbcTemplate();
    }

    public CityModel findByJdbcTemplate(Long id) {
        return cityReposiroty.findByJdbcTemplate(id);
    }

    /**
     * 底层调用CityMapper，走MybatisSingDatasourceConfig配置的Mybatis单一数据源
     *
     * @return
     */
    public List<CityModel> listByMybatis() {
        return cityReposiroty.listByMybatis();
    }

    /**
     * SqlSessionTemplateInterceptor切SqlSessionTemplate时，检测到update走主库
     * TransactionalInterceptor切DbSwitch时，检测到Transactional注解走主库
     *
     * @return
     */
    @Transactional
    public int updateByDynamicMybatisFromMaster() {
        return cityReposiroty.updateByDynamicMybatisFromMaster();
    }

    /**
     * SqlSessionTemplateInterceptor切SqlSessionTemplate时，检测到select走从库
     * TransactionalInterceptor切DbSwitch时，未检测到Transactional注解走从库
     *
     * @return
     */
    public List<CityModel> listByDynamicMybatisFromSlave() {
        return cityReposiroty.listByDynamicMybatisFromSlave();
    }
}
