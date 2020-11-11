package com.nutrymaco.jobsite.service.workschedule;

import com.nutrymaco.jobsite.entity.WorkSchedule;
import com.nutrymaco.jobsite.exception.found.WorkScheduleRepositoryNotFoundException;
import com.nutrymaco.jobsite.repository.WorkScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkScheduleServiceImpl implements WorkScheduleService {

    @Autowired
    WorkScheduleRepository scheduleRepository;

    @Override
    public WorkSchedule getById(Integer id) throws WorkScheduleRepositoryNotFoundException {
        return scheduleRepository.findById(id)
                .orElseThrow(WorkScheduleRepositoryNotFoundException::new);
    }

    @Override
    public List<WorkSchedule> getAllById(List<Integer> idList) {
        return scheduleRepository.findAllByIdIn(idList);
    }
}
