package com.nutrymaco.jobsite.config.data;

import com.nutrymaco.jobsite.entity.WorkSchedule;
import com.nutrymaco.jobsite.repository.WorkScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkScheduleData {

    @Autowired
    WorkScheduleRepository scheduleRepository;

    @Bean
    ApplicationRunner scheduleApplicationRunner() {
        return args -> {
            scheduleRepository.save(new WorkSchedule(1, "FUll"));
            scheduleRepository.save(new WorkSchedule(2, "PART"));
            scheduleRepository.save(new WorkSchedule(3, "FLEX"));
            scheduleRepository.save(new WorkSchedule(4, "OTHER"));
        };
    }
}
