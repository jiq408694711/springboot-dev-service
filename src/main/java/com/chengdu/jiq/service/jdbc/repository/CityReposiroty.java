package com.chengdu.jiq.service.jdbc.repository;

import com.chengdu.jiq.mapper.dynamic.DynamicCityMapper;
import com.chengdu.jiq.mapper.single.CityMapper;
import com.chengdu.jiq.model.bo.CityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by jiyiqin on 2017/11/5.
 */

@Repository
public class CityReposiroty {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private DynamicCityMapper dynamicCityMapper;

    public List<CityModel> listByJdbcTemplate() {
        return jdbcTemplate.query("select * from city", newRowMapper());
    }

    public CityModel findByJdbcTemplate(Long id) {
        return jdbcTemplate.queryForObject("select * from city where id = ?", newRowMapper(), id);
    }

    private RowMapper<CityModel> newRowMapper() {
        return new RowMapper<CityModel>() {
            @Override
            public CityModel mapRow(ResultSet resultSet, int i) throws SQLException {
                CityModel model = new CityModel();
                model.setId(resultSet.getInt("ID"));
                model.setName(resultSet.getString("Name"));
                model.setCountryCode(resultSet.getString("CountryCode"));
                model.setDistrict(resultSet.getString("District"));
                return model;
            }
        };
    }

    public List<CityModel> listByMybatis() {
        return cityMapper.list();
    }

    public int updateByDynamicMybatisFromMaster() {
        CityModel model = new CityModel();
        model.setId(1);
        model.setName("jiyiqin");
        return dynamicCityMapper.updateByDynamicMybatisFromMaster(model);
    }

    public List<CityModel> listByDynamicMybatisFromSlave() {
        return dynamicCityMapper.list();
    }
}
