package com.nutrymaco.jobsite.repository.vacancy;

import com.nutrymaco.jobsite.entity.Vacancy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends ElasticsearchRepository<Vacancy, String> {

    Vacancy findFirstByTitleAndDescriptionAndCityId(String title, String description, Integer city);

    List<Vacancy> findByIdIn(List<String> idList);
}
