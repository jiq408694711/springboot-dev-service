package com.chengdu.jiq.service.schedule.repository;

import com.chengdu.jiq.service.schedule.ScheduleModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jiyiqin on 2017/11/19.
 */
@Repository
public class ScheduleRepository {

    private Long index = 1L;
    private List<ScheduleModel> list = new ArrayList();

    public ScheduleModel find(Long id) {
        for (ScheduleModel model : list) {
            if (model.getId().equals(id)) {
                return model;
            }
        }
        return null;
    }

    public List<ScheduleModel> listValidScheduleModel() {
        Date now = new Date();
        return list.parallelStream().filter(model -> {
            return now.after(model.getStartDate()) && now.before(model.getEndDate()) && model.getStatus().equals("VALID");
        }).collect(Collectors.toList());
    }

    public ScheduleModel addScheduleModel(ScheduleModel model) {
        model.setId(index++);
        list.add(model);
        return model;
    }
}
