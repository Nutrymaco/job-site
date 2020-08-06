package com.nutrymaco.jobsite.entity;

import com.nutrymaco.jobsite.dto.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.GeneratedValue;
import java.util.Date;


@Getter
@Setter
@Builder
@Document(indexName = "site", createIndex = false)
public class Vacancy {

    @Id
    @GeneratedValue
    String id;

    String title;

    String company;

    String shortDescription;

    String description;

    int experienceFrom;

    int experienceTo;

    int salaryFrom;

    int salaryTo;

    Currency currency;

    String city;

    int cityId;

    String workSchedule;

    int workScheduleId;

    String url;

    Date date;
}
