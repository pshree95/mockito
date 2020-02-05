package com.example.mockito.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

@Configuration
@EnableScheduling
public class SalesforceConfig {

    @Bean
    public CloseableHttpClient httpClient() {

        return HttpClients.custom()
                .build();
    }
}
