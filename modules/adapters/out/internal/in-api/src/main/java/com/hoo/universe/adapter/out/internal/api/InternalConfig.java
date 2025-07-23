package com.hoo.universe.adapter.out.internal.api;

import com.hoo.universe.adapter.out.internal.api.file.GetFileInfoWebClientAdapter;
import com.hoo.universe.adapter.out.internal.api.file.UploadFileWebClientAdapter;
import com.hoo.universe.adapter.out.internal.api.user.GetUserInfoWebClientAdapter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(InternalProperties.class)
public class InternalConfig {

    @Bean
    public GetFileInfoWebClientAdapter getFileInfoWebClientAdapter(
            WebClient fileWebClient,
            InternalProperties internalProperties
    ) {

        return new GetFileInfoWebClientAdapter(fileWebClient, internalProperties);
    }

    @Bean
    public UploadFileWebClientAdapter uploadFileWebClientAdapter(
            WebClient fileWebClient,
            InternalProperties internalProperties
    ) {

        return new UploadFileWebClientAdapter(fileWebClient, internalProperties);
    }

    @Bean
    public GetUserInfoWebClientAdapter getUserInfoWebClientAdapter(
            WebClient userWebClient,
            InternalProperties internalProperties) {

        return new GetUserInfoWebClientAdapter(userWebClient, internalProperties);
    }

    @Bean
    public WebClient userWebClient(InternalProperties internalProperties) {

        return WebClient.builder()
                .baseUrl(internalProperties.getUser().getBaseUrl())
                .build();
    }

    @Bean
    public WebClient fileWebClient(InternalProperties internalProperties) {

        return WebClient.builder()
                .baseUrl(internalProperties.getFile().getBaseUrl())
                .build();
    }
}
