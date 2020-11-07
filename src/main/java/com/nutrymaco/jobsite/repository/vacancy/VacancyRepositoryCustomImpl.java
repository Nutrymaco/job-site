package com.nutrymaco.jobsite.repository.vacancy;

import com.nutrymaco.jobsite.adapter.elastisearch.ElasticVacancyQuery;
import com.nutrymaco.jobsite.dto.PaginationData;
import com.nutrymaco.jobsite.dto.VacancyFilter;
import com.nutrymaco.jobsite.entity.Vacancy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class VacancyRepositoryCustomImpl implements VacancyRepositoryCustom {

    @Autowired
    ElasticsearchRestTemplate restTemplate;

    @Autowired
    ElasticVacancyQuery.ElasticVacancyQueryBuilder queryBuilder;

    @Override
    public List<Vacancy> findByFilter(VacancyFilter filter) {
        final Query query = ElasticVacancyQuery.builder()
                .setFilter(filter)
                .setPaginationData(filter.getPaginationData())
                .build()
                .getElasticQuery();

        return restTemplate.search(query, Vacancy.class).stream()
                .map(SearchHit::getContent)
                .peek(v -> {
                    if (!filter.isIncludeDescription()) {
                        v.setDescription(null);
                    }
                })
                .collect(Collectors.toList());
    }
}
