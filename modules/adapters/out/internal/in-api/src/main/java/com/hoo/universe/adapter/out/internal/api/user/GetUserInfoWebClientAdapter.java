package com.hoo.universe.adapter.out.internal.api.user;

import com.hoo.common.internal.api.dto.UserInfo;
import com.hoo.universe.adapter.out.internal.api.InternalAPIConfigProperties;
import com.hoo.common.internal.api.GetUserInfoAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@RequiredArgsConstructor
public class GetUserInfoWebClientAdapter implements GetUserInfoAPI {

    private final WebClient webClient;
    private final InternalAPIConfigProperties internalAPIConfigProperties;

    @Override
    public UserInfo getUserInfo(UUID userID) {

        return webClient.get()
                .uri(String.format(internalAPIConfigProperties.getUser().getFindUserNicknameUrl(), userID))
                .retrieve()
                .bodyToMono(UserInfo.class)
                .block();
    }
}
