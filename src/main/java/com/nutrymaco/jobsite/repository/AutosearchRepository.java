package com.nutrymaco.jobsite.repository;

import com.nutrymaco.jobsite.dto.VacancyFilter;
import com.nutrymaco.jobsite.entity.Autosearch;
import com.nutrymaco.jobsite.entity.City;
import com.nutrymaco.jobsite.entity.WorkSchedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.util.MultiValueMap;

import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public interface AutosearchRepository extends CrudRepository<Autosearch, Integer> {
    @Query("select a.id " +
            "from Autosearch a " +
            "where a.text = :text " +
            "and a.expFrom = :expFrom " +
            "and a.expTo = :expTo " +
            "and a.salaryFrom = :salaryFrom " +
            "and a.salaryTo = :salaryTo " +
            "and a.cities = :cities " +
            "and a.workSchedules = :workSchedules")
    OptionalInt findByTextAndExpFromAndExpToAndSalaryFromAndExpToAndCitiesAndWorkSchedules(
                                String text,
                                int expFrom, int expTo,
                                int salaryFrom, int salaryTo,
                                Collection<City> cities,
                                Collection<WorkSchedule> workSchedules);

}
