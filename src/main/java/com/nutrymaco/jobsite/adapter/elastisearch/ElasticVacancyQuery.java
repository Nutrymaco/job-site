package com.nutrymaco.jobsite.adapter;

import com.nutrymaco.jobsite.dto.VacancyFilter;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

public class ElasticVacancyQuery {
    private int TITLE_BOOST = 10;
    private int PREFIX_LENGTH = 2;
    private VacancyFilter vacancyFilter;
    private NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

    private ElasticVacancyQuery() {}

    public static ElasticVacancyQueryBuilder builder() {
        return new ElasticVacancyQueryBuilder();
    }

    public Query getElasticQuery() {

    }

    private void addTextFilter() {

    }

    private void addExperienceFilter() {

    }

    private void addSalaryFilter() {

    }

    private void addCityFilter() {

    }

    private void addWorkScheduleFilter() {
        
    }

    static class ElasticVacancyQueryBuilder {
        private ElasticVacancyQuery query = new ElasticVacancyQuery();

        public ElasticVacancyQueryBuilder setTitleBoost(int titleBoost) {
            query.TITLE_BOOST = titleBoost;
            return this;
        }

        public ElasticVacancyQueryBuilder setPrefixLength(int prefixLength) {
            query.PREFIX_LENGTH = prefixLength;
            return this;
        }

        public ElasticVacancyQueryBuilder setFilter(VacancyFilter filter) {
            query.vacancyFilter = filter;
            return this;
        }

        public ElasticVacancyQuery build() {
            if (query.vacancyFilter == null){
                throw new IllegalStateException("filter does not set");
            }
            return query;
        }


    }
}
