package com.chengdu.jiq.mapper.single;

import com.chengdu.jiq.model.bo.CityModel;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Created by jiyiqin on 2017/11/6.
 */

@Mapper
public interface CityMapper {

    List<CityModel> list();
}
