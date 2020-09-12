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
import javax.persistence.FetchType;
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

    private int experience;

    private int salary;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private List<City> cities;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "work_schedule_id")
    private List<WorkSchedule> workSchedules;

    @Column(columnDefinition = "jsonb")
    @Type(type = "jsonb")
    List<String> lastDaySelectedBySearch;

    public void setFilter(VacancyFilter filter) {
        text = filter.getText();
        experience = filter.getExperience();
        salary = filter.getSalary();
        cities = filter.getCities();
        workSchedules = filter.getWorkSchedules();
    }

    public VacancyFilter extractFilter() {
        return VacancyFilter.builder()
                .text(text)
                .experience(experience)
                .salary(salary)
                .cities(cities)
                .workSchedules(workSchedules)
                .build();
    }
}
