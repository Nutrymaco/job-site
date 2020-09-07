package com.nutrymaco.jobsite.service.vacancy;

import com.nutrymaco.jobsite.dto.filter.Filters;
import com.nutrymaco.jobsite.dto.filter.RangeBoundType;
import com.nutrymaco.jobsite.dto.filter.RangeFilter;
import com.nutrymaco.jobsite.dto.filter.SelectFilter;
import com.nutrymaco.jobsite.entity.City;
import com.nutrymaco.jobsite.entity.WorkSchedule;
import com.nutrymaco.jobsite.repository.CityRepository;
import com.nutrymaco.jobsite.repository.WorkScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class VacancyAdvancedFiltersServiceImpl implements VacancyAdvancedFiltersService {
    @Autowired
    CityRepository cityRepository;

    @Autowired
    WorkScheduleRepository workScheduleRepository;

    @Bean
    public Filters getFilters() {
        System.out.println("init filters");
        Map<Integer, String> cities = new HashMap<>();
        Map<Integer, String> schedules = new HashMap<>();

        for (City c : cityRepository.findAll()) {
            cities.put(c.getId(), c.getName());
        }

        for (WorkSchedule s : workScheduleRepository.findAll()) {
            schedules.put(s.getId(), s.getName());
        }

        Filters filters = new Filters();
        filters.addFilter(new RangeFilter( "salary", RangeBoundType.SINGLE, 0, 1_000_000));
        filters.addFilter(new RangeFilter("experience", RangeBoundType.DOUBLE, 0, 10));
        filters.addFilter(new SelectFilter("city", cities, true));
        filters.addFilter(new SelectFilter("work schedule", schedules, true));
        return filters;
    }
}
