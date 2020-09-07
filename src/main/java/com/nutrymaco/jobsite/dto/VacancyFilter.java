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

    private final int experience;

    private final Integer salary;

    private final List<City> cities;

    private final List<WorkSchedule> workSchedules;

    @Override
    public String toString() {
        return "VacancyFilter{" +
                "text='" + text + '\'' +
                ", experience = " + experience +
                ", salary=" + salary +
                ", cities=" + cities +
                ", workSchedules=" + workSchedules +
                '}';
    }
}
