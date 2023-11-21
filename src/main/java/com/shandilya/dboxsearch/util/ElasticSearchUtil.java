package com.shandilya.dboxsearch.util;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.val;

import java.util.function.Supplier;

public class ElasticSearchUtil {

    public static Supplier<Query> supplierSearchAll() {
        return () -> Query.of(q -> q.matchAll(matchAllQuery()));
    }

    public static MatchAllQuery matchAllQuery() {
        val matchAllQuery = new MatchAllQuery.Builder();
        return matchAllQuery.build();
    }

    public static Supplier<Query> supplierWithFieldName(final String field) {
        return () -> Query.of(q -> q.match(matchQueryWithFieldName(field)));
    }

    public static MatchQuery matchQueryWithFieldName(final String field) {
        val matchQuery = new MatchQuery.Builder();
        return matchQuery.field("fileContent").query(field).build();
    }
}