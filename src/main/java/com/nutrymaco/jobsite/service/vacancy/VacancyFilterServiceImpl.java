package com.nutrymaco.jobsite.service.vacancy;

import com.nutrymaco.jobsite.dto.PaginationData;
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
                .experience(parseOrGetNull(filters.getFirst("experience")))
                .salary(parseOrGetNull(filters.getFirst("salary")))
                .cityIdList(filters.get("cityId").stream().map(Integer::parseInt).collect(Collectors.toList()))
                .workScheduleIdList(filters.get("workScheduleId").stream().map(Integer::parseInt).collect(Collectors.toList()))
                .size(parseOrGetNull(filters.getFirst("size")))
                .page(parseOrGetNull(filters.getFirst("page")))
                .includeDescription(Boolean.parseBoolean(filters.getFirst("includeDescription")))
                .build();


    }

    private Integer parseOrGetNull(String value) {
        if (value == null) {
            return null;
        } else {
            return Integer.parseInt(value);
        }
    }

    @Override
    public MultiValueMap<String, String> toMultiValueMap(VacancyFilter filter) {
        MultiValueMap<String, String> filters = new LinkedMultiValueMap<>();

        filters.set("text", filter.getText());
        filters.set("experience", String.valueOf(filter.getExperience()));
        filters.set("salary", String.valueOf(filter.getSalary()));
        filters.put("cityId", filter.getCityIdList().stream()
                                                    .map(Objects::toString)
                                                    .collect(Collectors.toList()));
        filters.put("workScheduleId", filter.getWorkScheduleIdList().stream()
                                                    .map(Objects::toString)
                                                    .collect(Collectors.toList()));
        return filters;
    }

    @Override
    public VacancyFilter fromVacancyFilterRequest(VacancyFilterRequest filterRequest) {
        return VacancyFilter.builder()
                .text(filterRequest.getText())
                .experience(filterRequest.getExperience())
                .salary(filterRequest.getSalary())
                .cityIdList(filterRequest.getCities())
                .workScheduleIdList(filterRequest.getWorkSchedules())
                .build();
    }
}
