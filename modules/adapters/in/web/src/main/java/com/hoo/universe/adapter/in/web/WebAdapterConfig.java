package com.hoo.universe.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class WebAdapterConfig {

    @Bean
    RequestMapper requestMapper(ObjectMapper webObjectMapper) {
        return new RequestMapper(webObjectMapper);
    }

    @Primary
    @Bean("webObjectMapper")
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
