package com.nutrymaco.jobsite.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VacancyDTO that = (VacancyDTO) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(company, that.company) &&
                Objects.equals(experienceFrom, that.experienceFrom) &&
                Objects.equals(experienceTo, that.experienceTo) &&
                Objects.equals(salaryFrom, that.salaryFrom) &&
                Objects.equals(salaryTo, that.salaryTo) &&
                currency == that.currency &&
                Objects.equals(city, that.city) &&
                Objects.equals(cityId, that.cityId) &&
                Objects.equals(workSchedule, that.workSchedule) &&
                Objects.equals(workScheduleId, that.workScheduleId) &&
                Objects.equals(url, that.url) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, company, experienceFrom, experienceTo, salaryFrom, salaryTo, currency, city, cityId, workSchedule, workScheduleId, url, date);
    }
}
