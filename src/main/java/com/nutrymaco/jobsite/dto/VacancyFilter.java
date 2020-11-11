package com.nutrymaco.jobsite.dto;

import com.nutrymaco.jobsite.entity.City;
import com.nutrymaco.jobsite.entity.WorkSchedule;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class VacancyFilter {
    private final String text;

    private final Integer experience;

    private final Integer salary;

    private final List<Integer> cityIdList;

    private final List<Integer> workScheduleIdList;

    private final Boolean includeDescription;

    private final Integer page;

    private final Integer size;

    public static VacancyFilter empty() {
        return VacancyFilter
                .builder()
                .build();
    }

    public boolean isNull() {
        return text == null && experience == null
                && salary == null && cityIdList == null
                && workScheduleIdList == null;
    }

    @Override
    public String toString() {
        return "VacancyFilter{" +
                "text='" + text + '\'' +
                ", experience = " + experience +
                ", salary=" + salary +
                ", cities=" + cityIdList +
                ", workSchedules=" + workScheduleIdList +
                '}';
    }
}
