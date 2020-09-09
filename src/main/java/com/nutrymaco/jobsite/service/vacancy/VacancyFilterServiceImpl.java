package com.nutrymaco.jobsite.service.vacancy;

import com.nutrymaco.jobsite.dto.VacancyFilter;
import com.nutrymaco.jobsite.dto.request.VacancyFilterRequest;
import com.nutrymaco.jobsite.entity.City;
import com.nutrymaco.jobsite.entity.WorkSchedule;
import com.nutrymaco.jobsite.repository.CityRepository;
import com.nutrymaco.jobsite.repository.WorkScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VacancyFilterServiceImpl implements VacancyFilterService {
    @Autowired
    WorkScheduleRepository scheduleRepository;

    @Autowired
    CityRepository cityRepository;

    @Override
    public VacancyFilter fromMultiValueMap(MultiValueMap<String, String> filters) {
        return VacancyFilter.builder()
                .text(filters.getFirst("text"))
                .experience(Integer.parseInt(
                        Optional.ofNullable(filters.getFirst("experience")).orElse(String.valueOf(Integer.MAX_VALUE))
                ))
                .salary(Integer.parseInt(
                        Optional.ofNullable(filters.getFirst("salary")).orElse("0")
                ))
                .cities(Optional.ofNullable(filters.get("cityId")).orElse(List.of()).stream()
                        .map(Integer::parseInt)
                        .map(id -> cityRepository.findById(id))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList()))
                .workSchedules(Optional.ofNullable(filters.get("workScheduleId"))
                                .orElse(List.of()).stream()
                                .map(Integer::parseInt)
                                .map((id) -> scheduleRepository.findById(id))
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .collect(Collectors.toList()))
                .build();


    }

    @Override
    public MultiValueMap<String, String> toMultiValueMap(VacancyFilter filter) {
        MultiValueMap<String, String> filters = new LinkedMultiValueMap<>();

        filters.set("text", filter.getText());
        filters.set("experience", String.valueOf(filter.getExperience()));
        filters.set("salary", String.valueOf(filter.getSalary()));
        filters.put("cityId", Optional.ofNullable(filter.getCities()).orElse(List.of())
                                                .stream()
                                                .map(City::getId)
                                                .map(Objects::toString)
                                                .collect(Collectors.toList()));
        filters.put("workScheduleId", Optional.ofNullable(filter.getWorkSchedules()).orElse(List.of())
                                                .stream()
                                                .map(WorkSchedule::getId)
                                                .map(Object::toString)
                                                .collect(Collectors.toList()));

        return filters;
    }

    @Override
    public VacancyFilter fromVacancyFilterRequest(VacancyFilterRequest filterRequest) {
        return VacancyFilter.builder()
                .text(filterRequest.getText())
                .experience(filterRequest.getExperience())
                .salary(filterRequest.getSalary())
                .cities(filterRequest.getCities().stream()
                                .map(id -> cityRepository.findById(id))
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .collect(Collectors.toList()))
                .workSchedules(filterRequest.getWorkSchedules().stream()
                                .map(id -> scheduleRepository.findById(id))
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .collect(Collectors.toList()))
                .build();
    }
}
