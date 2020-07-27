package com.nutrymaco.jobsite.entity;

import lombok.Builder;
import lombok.Getter;
import org.elasticsearch.client.ml.job.config.AnalysisConfig;
import org.elasticsearch.index.analysis.Analysis;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.completion.context.ContextMapping;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.CompletionContext;
import org.springframework.data.elasticsearch.annotations.CompletionField;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.GeneratedValue;


@Getter
@Builder
@Document(indexName = "site", indexStoreType = "vacancy")
@Setting(settingPath = "/Users/smykovefim/Documents/MyProjects/Java/jobsite/src/main/resources/elastic/vacancy_settings.json")
//@Mapping(mappingPath = "/Users/smykovefim/Documents/MyProjects/Java/jobsite/src/main/resources/elastic/vacancy_mappings.json")
public class Vacancy {

    @Id
    @GeneratedValue
    String id;

//    @CompletionField(analyzer = "autocomplete_analyzer")
//            @Field(type = FieldType.Text, analyzer = "autocomplete_index", searchAnalyzer = "autocomplete_search")
    String title;

    String description;

    int experienceFrom;

    int experienceTo;

    int salaryFrom;

    int salaryTo;

    String city;

    int cityId;

    String workSchedule;

    int workScheduleId;
}
