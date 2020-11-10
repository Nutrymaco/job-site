package com.nutrymaco.jobsite.dto.response;

import com.nutrymaco.jobsite.dto.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
public class VacancyResponse {
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

    String workSchedule;

    String url;

    Date date;
}
