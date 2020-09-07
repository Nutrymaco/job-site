package com.nutrymaco.jobsite.dto;

import com.nutrymaco.jobsite.entity.City;
import com.nutrymaco.jobsite.entity.WorkSchedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class VacancyFilter {
    private final String text;

    private final int expFrom;

    private final int expTo;

    private final Integer salaryFrom;

    private final Integer salaryTo;

    private final List<City> cities;

    private final List<WorkSchedule> workSchedules;

    @Override
    public String toString() {
        return "VacancyFilter{" +
                "text='" + text + '\'' +
                ", expFrom=" + expFrom +
                ", expTo=" + expTo +
                ", salaryFrom=" + salaryFrom +
                ", salaryTo=" + salaryTo +
                ", cities=" + cities +
                ", workSchedules=" + workSchedules +
                '}';
    }
}
