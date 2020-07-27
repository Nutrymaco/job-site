package com.nutrymaco.jobsite.repository;

import com.nutrymaco.jobsite.entity.WorkSchedule;
import org.springframework.data.repository.CrudRepository;

public interface WorkScheduleRepository extends CrudRepository<WorkSchedule, Integer> {
    WorkSchedule findByName(String name);
}
