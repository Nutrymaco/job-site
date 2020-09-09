package com.nutrymaco.jobsite.dto.request;

import com.nutrymaco.jobsite.dto.VacancyFilter;
import com.nutrymaco.jobsite.entity.Vacancy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class VacancyFilterRequest {
    private final String text;

    private final int experience;

    private final Integer salary;

    private final List<Integer> cities;

    private final List<Integer> workSchedules;


    @Override
    public String toString() {
        return "VacancyFilterRequest{" +
                "text='" + text + '\'' +
                ", experience=" + experience +
                ", salary=" + salary +
                ", cities=" + cities +
                ", workSchedules=" + workSchedules +
                '}';
    }
}
