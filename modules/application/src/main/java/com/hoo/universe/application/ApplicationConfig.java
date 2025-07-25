package com.hoo.universe.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public FileOwnerMapExtractor fileIdCollector() {
        return new FileOwnerMapExtractor();
    }

    @Bean
    public ApplicationMapper applicationMapper() {
        return new ApplicationMapper();
    }
}
