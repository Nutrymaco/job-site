package com.nutrymaco.jobsite.adapter.elastisearch;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.List;

public class ElasticUtils {
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
