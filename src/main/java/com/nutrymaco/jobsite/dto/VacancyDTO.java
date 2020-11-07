package com.nutrymaco.jobsite.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class VacancyDTO {

    String id;

    String title;

    String description;

    String company;

    Integer experienceFrom;

    Integer experienceTo;

    Integer salaryFrom;

    Integer salaryTo;

    Currency currency;

    String city;

    Integer cityId;

    String workSchedule;

    Integer workScheduleId;

    String url;

    Date date;
}
