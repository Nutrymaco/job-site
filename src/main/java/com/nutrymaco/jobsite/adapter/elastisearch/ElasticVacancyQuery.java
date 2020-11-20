package com.nutrymaco.jobsite.adapter.elastisearch;

import com.nutrymaco.jobsite.dto.PaginationData;
import com.nutrymaco.jobsite.dto.VacancyFilter;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

import static com.nutrymaco.jobsite.adapter.elastisearch.ElasticUtils.addMultiValueMatch;
import static com.nutrymaco.jobsite.adapter.elastisearch.ElasticUtils.addRangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

public class ElasticVacancyQuery {
    private int TITLE_BOOST;
    private int PREFIX_LENGTH;
    private VacancyFilter vacancyFilter;
    private final NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
    private final BoolQueryBuilder filterBuilder = QueryBuilders.boolQuery();
    private int countOfFilters = 0;
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
        filterBuilder.minimumShouldMatch(countOfFilters);

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
            countOfFilters++;
        }
    }

    private void addExperienceRange() {
        if (vacancyFilter.getExperience() != null) {
            filterBuilder.should(QueryBuilders.boolQuery()
                    .should(QueryBuilders.boolQuery()
                        .should(QueryBuilders.rangeQuery("experienceFrom")
                                            .lte(vacancyFilter.getExperience()))
                        .should(QueryBuilders.rangeQuery("experienceTo")
                                            .gte(vacancyFilter.getExperience()))
                        .minimumShouldMatch(2)
                    )
                    .should(QueryBuilders.boolQuery()
                        .should(QueryBuilders.rangeQuery("experienceFrom")
                                            .lte(vacancyFilter.getExperience()))
                        .mustNot(QueryBuilders.existsQuery("experienceTo"))
                        .minimumShouldMatch(1)
                    )
                    .should(QueryBuilders.boolQuery()
                        .should(QueryBuilders.rangeQuery("experienceTo")
                                            .lte(vacancyFilter.getExperience()))
                        .mustNot(QueryBuilders.existsQuery("experienceFrom"))
                        .minimumShouldMatch(1)
                    ).minimumShouldMatch(1)

            );
            countOfFilters++;
        }
    }

    private void addSalaryRange() {
        if (vacancyFilter.getSalary() != null) {
            countOfFilters++;
            filterBuilder.should(QueryBuilders.boolQuery()
                    .should(QueryBuilders.existsQuery("salaryFrom"))
                    .should(QueryBuilders.rangeQuery("salaryTo")
                            .gte(vacancyFilter.getSalary()))
                    .mustNot(QueryBuilders.rangeQuery("salaryTo")
                            .lte(vacancyFilter.getSalary()))
                    .minimumShouldMatch(1)
            );
        }
    }

    private void addCityFilter() {
        if (vacancyFilter.getCityIdList() != null && !vacancyFilter.getCityIdList().isEmpty()){
            countOfFilters++;
            addMultiValueMatch(filterBuilder,
                    "cityId", vacancyFilter.getCityIdList());
        }

    }

    private void addWorkScheduleFilter() {
        if (vacancyFilter.getWorkScheduleIdList() != null && !vacancyFilter.getWorkScheduleIdList().isEmpty()) {
            countOfFilters++;
            addMultiValueMatch(filterBuilder,
                    "workScheduleId", vacancyFilter.getWorkScheduleIdList());
        }

    }

    public static class ElasticVacancyQueryBuilder {
        private final ElasticVacancyQuery query = new ElasticVacancyQuery();

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
