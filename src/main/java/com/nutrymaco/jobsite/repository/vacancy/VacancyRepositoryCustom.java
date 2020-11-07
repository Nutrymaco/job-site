package com.nutrymaco.jobsite.repository.vacancy;

import com.nutrymaco.jobsite.dto.VacancyFilter;
import com.nutrymaco.jobsite.entity.Vacancy;

import java.util.List;

public interface VacancyRepositoryCustom {

    List<Vacancy> findByFilter(VacancyFilter filter);

}
