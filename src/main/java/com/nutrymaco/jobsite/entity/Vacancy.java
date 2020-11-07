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

    Integer experienceFrom;

    Integer experienceTo;

    Integer salaryFrom;

    Integer salaryTo;

    Currency currency;

    Integer cityId;

    Integer workScheduleId;

    String url;

    Date date;
}
