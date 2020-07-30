package com.nutrymaco.jobsite.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class VacancyDTO {
    String title;

    String description;

    String company;

    int experienceFrom;

    int experienceTo;

    int salaryFrom;

    int salaryTo;

    String city;

    int cityId;

    String workSchedule;

    int workScheduleId;

    String url;
}
