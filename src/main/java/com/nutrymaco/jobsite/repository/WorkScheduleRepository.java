package com.nutrymaco.jobsite.repository;

import com.nutrymaco.jobsite.entity.WorkSchedule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WorkScheduleRepository extends CrudRepository<WorkSchedule, Integer> {
    WorkSchedule findByName(String name);
    List<WorkSchedule> findAllByIdIn(List<Integer> id);
}
