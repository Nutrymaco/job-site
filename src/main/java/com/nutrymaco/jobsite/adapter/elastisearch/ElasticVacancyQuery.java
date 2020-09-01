package com.nutrymaco.jobsite.adapter.elastisearch;

import com.nutrymaco.jobsite.dto.VacancyFilter;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;
import static com.nutrymaco.jobsite.adapter.elastisearch.ElasticUtils.*;

public class ElasticVacancyQuery {
    private int TITLE_BOOST = 10;
    private int PREFIX_LENGTH = 2;
    private VacancyFilter vacancyFilter;
    private NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
    BoolQueryBuilder filterBuilder = QueryBuilders.boolQuery();


    private ElasticVacancyQuery() {}

    public static ElasticVacancyQueryBuilder builder() {
        return new ElasticVacancyQueryBuilder();
    }

    public Query getElasticQuery() {
        addTextFilter();
        addCityFilter();
        addWorkScheduleFilter();
        addExperienceRange();
        addSalaryRange();

        return queryBuilder
                .withFilter(filterBuilder)
                .withPageable(Pageable.unpaged())
                .build();
    }

    private void addTextFilter() {
        if (vacancyFilter.getText() != null) {
            queryBuilder.withQuery(multiMatchQuery(vacancyFilter.getText())
                    .field("title").boost(TITLE_BOOST)
                    .field("description.search")
                    .fuzziness(Fuzziness.ONE)
                    .prefixLength(PREFIX_LENGTH));
        }
    }

    private void addExperienceRange() {
        addRangeQuery(filterBuilder,
                "experienceFrom", "experienceTo",
                vacancyFilter.getExpFrom(),
                vacancyFilter.getExpTo());
    }

    private void addSalaryRange() {
        addRangeQuery(filterBuilder,
                "salaryFrom", "salaryTo",
                vacancyFilter.getSalaryFrom());
    }

    private void addCityFilter() {
        if (vacancyFilter.getCities() != null) {
            addMultiValueMatch(filterBuilder,
                    "city", List.of(vacancyFilter.getCities()));
        }
    }

    private void addWorkScheduleFilter() {
        if (vacancyFilter.getWorkSchedules() != null) {
            addMultiValueMatch(filterBuilder,
                    "workSchedule", List.of(vacancyFilter.getWorkSchedules()));
        }
    }

    public static class ElasticVacancyQueryBuilder {
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
