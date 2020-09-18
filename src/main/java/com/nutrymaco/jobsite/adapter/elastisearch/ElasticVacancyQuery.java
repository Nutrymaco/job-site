package com.nutrymaco.jobsite.adapter.elastisearch;

import com.nutrymaco.jobsite.dto.PaginationData;
import com.nutrymaco.jobsite.dto.VacancyFilter;
import com.nutrymaco.jobsite.entity.City;
import com.nutrymaco.jobsite.entity.WorkSchedule;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;
import static com.nutrymaco.jobsite.adapter.elastisearch.ElasticUtils.*;

@Slf4j
public class ElasticVacancyQuery {
    private int TITLE_BOOST = 10;
    private int PREFIX_LENGTH = 2;
    private VacancyFilter vacancyFilter;
    private NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
    private BoolQueryBuilder filterBuilder = QueryBuilders.boolQuery();
    private int COUNT_OF_FILTERS = 3;
    private PaginationData paginationData;

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
        filterBuilder.minimumShouldMatch(COUNT_OF_FILTERS);

        log.info(String.valueOf(queryBuilder.withFilter(filterBuilder).build().getQuery()));
        log.info(String.valueOf(queryBuilder.withFilter(filterBuilder).build().getFilter()));

        return queryBuilder
                .withFilter(filterBuilder)
                .withPageable(paginationData.getPageable())
                .build();
    }

    private void addTextFilter() {
        if (vacancyFilter.getText() != null && !vacancyFilter.getText().equals("")) {
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
                0,
                vacancyFilter.getExperience());
    }

    private void addSalaryRange() {
        addRangeQuery(filterBuilder, "salaryTo",
                vacancyFilter.getSalary());
    }

    private void addCityFilter() {
        if (!vacancyFilter.getCities().isEmpty()){
            COUNT_OF_FILTERS++;
            addMultiValueMatch(filterBuilder,
                    "city", vacancyFilter.getCities()
                            .stream()
                            .map(City::getName)
                            .collect(Collectors.toList()));
        }

    }

    private void addWorkScheduleFilter() {
        if (!vacancyFilter.getWorkSchedules().isEmpty()) {
            COUNT_OF_FILTERS++;
            addMultiValueMatch(filterBuilder,
                    "workSchedule", vacancyFilter.getWorkSchedules()
                            .stream()
                            .map(WorkSchedule::getName)
                            .collect(Collectors.toList()));
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

        public ElasticVacancyQueryBuilder setPaginationData(PaginationData paginationData) {
            query.paginationData = paginationData;
            return this;
        }

        public ElasticVacancyQuery build() {
            if (query.vacancyFilter == null){
                throw new IllegalStateException("filter does not set or equals null");
            }
            return query;
        }


    }
}
