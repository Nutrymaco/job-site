package com.nutrymaco.jobsite.adapter;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.neo4j.repository.query.filter.FilterBuilder;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;


public class UrlParamsToElasticQuery {
    static public Query getQueryFromVacancyParams(MultiValueMap<String, String> params) {
        List<String> text = params.get("text");
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder filterBuilder = QueryBuilders.boolQuery();

        if (text != null) {
            queryBuilder = queryBuilder.withQuery(multiMatchQuery(text.get(0))
                            .field("title").boost(2)
                            .field("description.search")
                            .fuzziness(Fuzziness.ONE)
                            .prefixLength(2));
        }

        String minExp = params.getFirst("expFrom");
        String maxExp = params.getFirst("expTo");
        try {
            filterBuilder = addRangeQuery(filterBuilder,
                    "experienceFrom", "experienceTo",
                    minExp == null ? 0 : Integer.parseInt(minExp),
                    maxExp == null ? Integer.MAX_VALUE : Integer.parseInt(maxExp));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("when parsing experience parameters");
        }

        String salary = params.getFirst("salary");
        try {
            filterBuilder = addRangeQuery(filterBuilder,
                    "salaryFrom", "salaryTo",
                    salary == null ? 0 : Integer.parseInt(salary));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("when parsing salary parameter");
        }


        List<String> cityId = params.get("cityId");
        if (cityId != null) {
            filterBuilder = addMultiValueMatch(filterBuilder,
                                                "cityId", cityId);
        }

        List<String> scheduleId = params.get("scheduleId");
        if (scheduleId != null) {
            filterBuilder = addMultiValueMatch(filterBuilder,
                                                "scheduleId", scheduleId);
        }

        Pageable pageable = Pageable.unpaged();
        String page, size;
        try {
            if ((page = params.getFirst("page")) != null && (size = params.getFirst("size")) != null) {
                pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(size));
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("when parsing pageable parameters");
        }

//        System.out.println(queryBuilder.withFilter(filterBuilder).build().getQuery());
//        System.out.println(queryBuilder.withFilter(filterBuilder).build().getFilter());
        return queryBuilder
                .withFilter(filterBuilder)
                .withPageable(pageable)
                .build();
    }

    static BoolQueryBuilder addRangeQuery(BoolQueryBuilder filterBuilder,
                                      String fieldFrom, String fieldTo,
                                      int min, int max) {
        return filterBuilder
                .should(QueryBuilders.rangeQuery(fieldFrom)
                        .gte(min)
                        .lte(max))
                .should(QueryBuilders.rangeQuery(fieldTo)
                        .gte(min)
                        .lte(max))
                .minimumShouldMatch(1);
    }

    static BoolQueryBuilder addRangeQuery(BoolQueryBuilder filterBuilder,
                                      String fieldFrom, String fieldTo,
                                      int min) {
        return filterBuilder
                .should(QueryBuilders.rangeQuery(fieldFrom)
                        .gte(min))
                .should(QueryBuilders.rangeQuery(fieldTo)
                        .gte(min))
                .minimumShouldMatch(1);
    }

    static <V>BoolQueryBuilder addMultiValueMatch(BoolQueryBuilder filterBuilder,
                                               String field, List<V> values) {
        for (V value : values) {
            filterBuilder = filterBuilder
                    .should(QueryBuilders.matchQuery(field, value));
        }
        return filterBuilder
                .minimumShouldMatch(1);
    }
}
