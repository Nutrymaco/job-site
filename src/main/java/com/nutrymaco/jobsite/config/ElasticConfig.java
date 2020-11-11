package com.nutrymaco.jobsite.config;

import com.nutrymaco.jobsite.adapter.elastisearch.ElasticVacancyQuery;
import com.nutrymaco.jobsite.util.File;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Slf4j
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.nutrymaco.jobsite.repository")
@ComponentScan
public class ElasticConfig {

    @Value("${search.title-boost}")
    private int titleBoost;

    @Value("${search.prefix-length}")
    private int prefixLength;

    @Value("${elastic.create-index}")
    private boolean createIndex;

    @Value("${elastic.host}")
    private String host;

    @Value("${elastic.port}")
    private int port;

    @Bean
    public RestHighLevelClient client() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(String.format("%s:%d", host, port))
                .withBasicAuth("elastic", "Hrt23hgerheh")
                .build();
        System.out.println("RHC created");

        return RestClients.create(clientConfiguration).rest();
    }

    @Bean
    public ElasticsearchOperations elasticsearchOperations() {
        return new ElasticsearchRestTemplate(client());
    }

    @Bean
    public ElasticsearchRestTemplate elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(client());
    }

    @Bean
    public ElasticVacancyQuery.ElasticVacancyQueryBuilder vacancyQueryBuilder() {
        return ElasticVacancyQuery.builder()
                    .setTitleBoost(titleBoost)
                    .setPrefixLength(prefixLength);
    }

    @Bean
    public ApplicationRunner createIndex() {
        if (!createIndex)
            return args -> {};
        return args -> {
            RestHighLevelClient client = client();
            try {
                client.indices().delete(new DeleteIndexRequest().indices("site"), RequestOptions.DEFAULT);
            } catch (Exception ignored) {

            }
            CreateIndexRequest request = new CreateIndexRequest("site");
            request.source(File.loadFromFile("/settings/all_settings.json"), XContentType.JSON);
            System.out.println(client.indices().create(request, RequestOptions.DEFAULT).index());
        };
    }
}
