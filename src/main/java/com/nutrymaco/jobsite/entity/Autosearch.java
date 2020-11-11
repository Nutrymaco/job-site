package com.nutrymaco.jobsite.entity;

import com.nutrymaco.jobsite.dto.VacancyFilter;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "autosearch")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Autosearch {
    @Id
    @GeneratedValue
    int id;

    private String text;

    private int experience;

    private int salary;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "city_id")
    private List<City> cities;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "work_schedule_id")
    private List<WorkSchedule> workSchedules;

    @Column(columnDefinition = "jsonb")
    @Type(type = "jsonb")
    List<String> lastDaySelectedBySearch;

    public VacancyFilter extractFilter() {
        return VacancyFilter.builder()
                .text(text)
                .experience(experience)
                .salary(salary)
                .cityIdList(cities.stream().map(City::getId).collect(Collectors.toList()))
                .workScheduleIdList(workSchedules.stream().map(WorkSchedule::getId).collect(Collectors.toList()))
                .build();
    }
}
