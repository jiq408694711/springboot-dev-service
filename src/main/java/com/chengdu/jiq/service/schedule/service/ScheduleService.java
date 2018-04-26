package com.chengdu.jiq.service.schedule.service;

import com.chengdu.jiq.common.annotation.DbSwitch;
import com.chengdu.jiq.service.schedule.ScheduleModel;
import com.chengdu.jiq.service.schedule.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiyiqin on 2017/11/19.
 */
@DbSwitch
@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public ScheduleModel findModel(Long id) {
        return scheduleRepository.find(id);
    }

    public List<ScheduleModel> listValidScheduleModel() {
        return scheduleRepository.listValidScheduleModel();
    }

    public ScheduleModel addScheduleModel(ScheduleModel model) {
        return scheduleRepository.addScheduleModel(model);
    }
}
