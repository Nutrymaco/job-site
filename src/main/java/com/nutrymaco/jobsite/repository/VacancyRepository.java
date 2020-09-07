package com.nutrymaco.jobsite.repository;

import com.nutrymaco.jobsite.entity.Vacancy;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends ElasticsearchRepository<Vacancy, String> {
    Vacancy findFirstByTitleAndDescriptionAndCity(String title, String description, String city);
}
