package com.hoo.universe.adapter.out.internal.api;

import com.hoo.common.internal.api.user.GetUserInfoAPI;
import com.hoo.common.internal.api.user.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@RequiredArgsConstructor
public class UserWebClientAdapter implements GetUserInfoAPI {

    private final WebClient webClient;
    private final InternalProperties internalProperties;

    @Override
    public UserInfo getUserInfo(UUID userID) {

        return webClient.get()
                .uri(String.format(internalProperties.getUser().getGetUserInfoUrl(), userID))
                .retrieve()
                .bodyToMono(UserInfo.class)
                .block();
    }
}
