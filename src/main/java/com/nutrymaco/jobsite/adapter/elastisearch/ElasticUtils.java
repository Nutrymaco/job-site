package com.nutrymaco.jobsite.adapter.elastisearch;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.List;

public class ElasticUtils {
    static BoolQueryBuilder addRangeQuery(BoolQueryBuilder filterBuilder,
                                          String field, int min) {
        return filterBuilder
                .should(QueryBuilders.rangeQuery(field)
                            .lte(Integer.MAX_VALUE)
                            .gte(min));
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
                        .lte(max));
    }

    static BoolQueryBuilder addRangeQuery(BoolQueryBuilder filterBuilder,
                                          String fieldFrom, String fieldTo,
                                          int min) {

        return filterBuilder
                .should(QueryBuilders.rangeQuery(fieldFrom)
                        .gte(min))
                .should(QueryBuilders.rangeQuery(fieldTo)
                        .gte(min))
//                        .lte(Integer.MAX_VALUE)) //?
                        ;
    }

    static <V>BoolQueryBuilder addMultiValueMatch(BoolQueryBuilder filterBuilder,
                                                  String field, List<V> values) {
        for (V value : values) {
            filterBuilder.should(QueryBuilders.matchQuery(field, value));
        }
        return filterBuilder;
    }
}
