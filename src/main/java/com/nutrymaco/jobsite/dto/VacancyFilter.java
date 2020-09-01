package com.nutrymaco.jobsite.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class VacancyFilter {
    private final String text;

    private final int expFrom;

    private final int expTo;

    private final int salaryFrom;

    private final int salaryTo;

    private final List<String> cities;

    private final List<String> workSchedules;
}
