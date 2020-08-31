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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VacancyAdvancedFiltersServiceImpl implements VacancyAdvancedFiltersService {
    @Autowired
    CityRepository cityRepository;

    @Autowired
    WorkScheduleRepository workScheduleRepository;

    public Filters getFilters() {
        System.out.println("init filters");
        List<String> cities = new ArrayList<>();
        List<String> schedules = new ArrayList<>();

        for (City c : cityRepository.findAll()) {
            cities.add(c.getName());
            System.out.println(c.getName());
        }

        for (WorkSchedule s : workScheduleRepository.findAll()) {
            schedules.add(s.getName());
        }

        Filters filters = new Filters();
        filters.addFilter(new RangeFilter( "salary", RangeBoundType.SINGLE, 0, 1_000_000));
        filters.addFilter(new RangeFilter("experience", RangeBoundType.DOUBLE, 0, 10));
        filters.addFilter(new SelectFilter("city", cities, true));
        filters.addFilter(new SelectFilter("work schedule", schedules, true));
        return filters;
    }
}
