package com.chengdu.jiq.service.schedule.timer;

import com.chengdu.jiq.common.schedule.PlatformTask;
import com.chengdu.jiq.common.schedule.PlatformTaskScheduler;
import com.chengdu.jiq.service.schedule.ScheduleModel;
import com.chengdu.jiq.service.schedule.service.ScheduleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jiyiqin on 2017/11/19.
 */
@Component
public class MyScheduleScheduler extends PlatformTaskScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyScheduleScheduler.class);

    @Autowired
    private ScheduleService scheduleService;

    @Scheduled(fixedRate = 1000 * 10 * 1)
    public void schedule() {
        super.schedule();
    }

    @Override
    protected List<PlatformTask<ScheduleModel>> getSchedulableTask() {
        return scheduleService.listValidScheduleModel().parallelStream().map(model -> new MyScheduleTask(model)).collect(Collectors.toList());
    }

    /**
     * 内部类，自定义调度任务
     */
    private class MyScheduleTask extends PlatformTask<ScheduleModel> {

        public MyScheduleTask(ScheduleModel model) {
            super(model);
        }

        @Override
        protected boolean expire(String identity) {
            ScheduleModel model = scheduleService.findModel(Long.parseLong(identity));
            Date now = new Date();
            return now.before(model.getStartDate()) && now.after(model.getEndDate()) && model.getStatus().equals("FINISH");
        }

        @Override
        protected void execute(ScheduleModel module) {
            LOGGER.info("send msg out: {}", module.getMsg()); //or do anything, such as acquire 0-1 semaphore
        }

        @Override
        protected String identity(ScheduleModel module) {
            return module.getId().toString();
        }

        @Override
        protected CronTrigger cronTrigger(ScheduleModel schedule) {
            String cronExpress = null;
            switch (schedule.getScheduleType()) {
                case PERIOD_DATE:
                    cronExpress = "0 0 " + schedule.getHour() + " " + (null == schedule.getDay() ? "*" : schedule.getDay()) + " * ?";
                    break;
                case DELAY_DATE:
                    Calendar delayDate = Calendar.getInstance();
                    delayDate.setTime(schedule.getDelayDate());
                    cronExpress = delayDate.get(Calendar.SECOND) + " " + delayDate.get(Calendar.MINUTE) + " " + delayDate.get(Calendar.HOUR_OF_DAY) + " " + delayDate.get(Calendar.DAY_OF_MONTH) + " " + (delayDate.get(Calendar.MONTH) + 1) + " ?";
            }
            if (StringUtils.isNoneBlank(cronExpress)) {
                return new CronTrigger(cronExpress);
            }
            return null;
        }
    }
}
