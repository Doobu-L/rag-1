package com.prac.rag1.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.TokenCountBatchingStrategy;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VectorStoreConfig {

    private final EmbeddingModel embeddingModel;

    public VectorStoreConfig(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @Bean
    public RestClient restClient() {
        return RestClient.builder(new HttpHost("localhost", 9200, "http"))
                .build();
    }
    @Bean
    public VectorStore vectorStore(RestClient restClient, EmbeddingModel embeddingModel) {

        return org.springframework.ai.vectorstore.elasticsearch.ElasticsearchVectorStore.builder(restClient, embeddingModel)
                .batchingStrategy(new TokenCountBatchingStrategy()) // Optional: defaults to TokenCountBatchingStrategy
                .build();
    }

}