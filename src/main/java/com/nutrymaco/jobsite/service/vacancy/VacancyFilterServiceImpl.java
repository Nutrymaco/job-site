package com.nutrymaco.jobsite.service.vacancy;

import com.nutrymaco.jobsite.dto.VacancyFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

@Service
public class VacancyFilterServiceImpl implements VacancyFilterService {
    @Override
    public VacancyFilter fromMultiValueMap(MultiValueMap<String, String> filters) {
        return VacancyFilter.builder()
                .text(filters.getFirst("text"))
                .expFrom(Integer.parseInt(
                        Optional.ofNullable(filters.getFirst("expFrom")).orElse("0")
                ))
                .expTo(Integer.parseInt(
                        Optional.ofNullable(filters.getFirst("expTo")).orElse("2147483647")
                ))
                .salaryFrom(Integer.parseInt(
                        Optional.ofNullable(filters.getFirst("salaryFrom")).orElse("0")
                ))
                .salaryTo(Integer.parseInt(
                        Optional.ofNullable(filters.getFirst("salaryTo")).orElse("2147483647")
                ))
                .cities(filters.get("city"))
                .workSchedules(filters.get("workSchedule"))
                .build();


    }

    @Override
    public MultiValueMap<String, String> toMultiValueMap(VacancyFilter filter) {
        MultiValueMap<String, String> filters = new LinkedMultiValueMap<>();

        filters.set("text", filter.getText());
        filters.set("expFrom", String.valueOf(filter.getExpFrom()));
        filters.set("expTo", String.valueOf(filter.getExpTo()));
        filters.set("salaryFrom", String.valueOf(filter.getSalaryFrom()));
        filters.set("salaryTo", String.valueOf(filter.getSalaryTo()));
        filters.put("city", filter.getCities());
        filters.put("workSchedule", filter.getWorkSchedules());

        return filters;
    }
}
