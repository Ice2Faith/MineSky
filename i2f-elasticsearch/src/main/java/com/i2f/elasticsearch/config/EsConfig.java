package com.i2f.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

/**
 * @author ltb
 * @date 2021/9/13
 */
@Configuration
public class EsConfig {

    @Value("${elasticsearch.url:http://localhost:9200}")
    private String esUrl;

    @Bean("esConfigRestClientBuilder")
    public RestClientBuilder restClientBuilder(){
        URI uri=URI.create(esUrl);
        HttpHost host=new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
        return RestClient.builder(host);
    }

    @Bean("esConfigRestHighLevelClient")
    public RestHighLevelClient highLevelClient(@Autowired @Qualifier("esConfigRestClientBuilder") RestClientBuilder builder){
        return new RestHighLevelClient(builder);
    }

}
