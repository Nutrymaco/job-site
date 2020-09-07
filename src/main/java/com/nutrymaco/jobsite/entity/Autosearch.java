package com.nutrymaco.jobsite.entity;

import com.nutrymaco.jobsite.dto.VacancyFilter;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.util.MultiValueMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "autosearch")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Autosearch {
    @Id
    @GeneratedValue
    int id;

    private String text;

    private int expFrom;

    private int expTo;

    private int salaryFrom;

    private int salaryTo;

    @OneToMany()
    @JoinColumn(name = "city_id")
    private List<City> cities;

    @OneToMany
    @JoinColumn(name = "work_schedule_id")
    private List<WorkSchedule> workSchedules;

    @Column(columnDefinition = "jsonb")
    @Type(type = "jsonb")
    List<String> lastDaySelectedBySearch;

    public void setFilter(VacancyFilter filter) {
        text = filter.getText();
        expFrom = filter.getExpFrom();
        expTo = filter.getExpTo();
        salaryFrom = filter.getSalaryFrom();
        salaryTo = filter.getSalaryTo();
        cities = filter.getCities();
        workSchedules = filter.getWorkSchedules();
    }

    public VacancyFilter getFilter() {
        return VacancyFilter.builder()
                .text(text)
                .expFrom(expFrom)
                .expTo(expTo)
                .salaryFrom(salaryFrom)
                .salaryTo(salaryTo)
                .cities(cities)
                .workSchedules(workSchedules)
                .build();
    }
}
