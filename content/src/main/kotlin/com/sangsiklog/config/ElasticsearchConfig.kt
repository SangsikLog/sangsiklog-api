package com.sangsiklog.config

import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate

@Configuration
class ElasticsearchConfig {

    @Value("\${spring.data.elasticsearch.hostname}")
    private lateinit var hostName: String

    @Value("\${spring.data.elasticsearch.port}")
    private lateinit var port: String

    @Value("\${spring.data.elasticsearch.schema}")
    private lateinit var schema: String

    @Bean
    fun elasticsearchClient(): RestHighLevelClient {
        val clientBuilder = RestClient.builder(HttpHost(hostName, port.toInt(), schema))
        return RestHighLevelClient(clientBuilder)
    }

    @Bean
    fun elasticsearchTemplate(client: RestHighLevelClient): ElasticsearchRestTemplate {
        return ElasticsearchRestTemplate(client)
    }
}