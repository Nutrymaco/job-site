package com.nutrymaco.jobsite.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.GeneratedValue;
import java.util.Date;


@Getter
@Builder
@Document(indexName = "site", createIndex = false)
public class Vacancy {

    @Id
    @GeneratedValue
    String id;

    String title;

    String company;

    String description;

    int experienceFrom;

    int experienceTo;

    int salaryFrom;

    int salaryTo;

    String city;

    int cityId;

    String workSchedule;

    int workScheduleId;

    String url;

    Date date;
}
