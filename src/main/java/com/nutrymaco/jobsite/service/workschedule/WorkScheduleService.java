package com.nutrymaco.jobsite.service.workschedule;

import com.nutrymaco.jobsite.entity.WorkSchedule;
import com.nutrymaco.jobsite.exception.found.WorkScheduleRepositoryNotFoundException;

import java.util.List;

public interface WorkScheduleService {
    WorkSchedule getById(Integer id) throws WorkScheduleRepositoryNotFoundException;
    List<WorkSchedule> getAllById(List<Integer> idList);
}
