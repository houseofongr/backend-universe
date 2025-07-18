package com.hoo.aar.application.service;

import com.hoo.aar.adapter.out.jwt.JwtAdapter;
import com.hoo.aar.application.port.in.authn.OAuth2Dto;
import com.hoo.aar.application.port.in.user.SearchMyInfoResult;
import com.hoo.aar.application.port.out.persistence.user.SearchUserPort;
import com.hoo.aar.application.service.authn.LoadKakaoSnsAccountService;
import com.hoo.admin.application.port.in.snsaccount.CreateSnsAccountUseCase;
import com.hoo.admin.application.port.in.snsaccount.LoadSnsAccountUseCase;
import com.hoo.admin.domain.user.snsaccount.SnsAccount;
import com.hoo.admin.domain.user.snsaccount.SnsDomain;
import com.hoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.InstanceOfAssertFactories.BOOLEAN;
import static org.mockito.Mockito.*;

class LoadKakaoSnsAccountServiceTest {

    LoadKakaoSnsAccountService sut;

    LoadSnsAccountUseCase loadSnsAccountUseCase;
    SearchUserPort searchUserPort;
    CreateSnsAccountUseCase createSnsAccountUseCase;
    JwtAdapter jwtAdapter;

    @BeforeEach
    void init() {
        loadSnsAccountUseCase = mock();
        searchUserPort = mock();
        createSnsAccountUseCase = mock();
        jwtAdapter = mock(JwtAdapter.class);
        sut = new LoadKakaoSnsAccountService(loadSnsAccountUseCase, searchUserPort, createSnsAccountUseCase, jwtAdapter);
    }

    @Test
    @DisplayName("DB에 존재하는 계정은 isFirstLogin = false")
    void testExistUser() {
        // given
        OAuth2User oAuth2User = mock();
        SnsAccount snsAccount = SnsAccount.load(1L, SnsDomain.KAKAO, "sns_id", "leaf", "leaf", "test@example.com", null, null, 1L);
        SearchMyInfoResult result = mock();

        // when
        when(loadSnsAccountUseCase.loadSnsAccount(any(), any())).thenReturn(snsAccount);
        when(searchUserPort.queryMyInfo(1L)).thenReturn(result);
        when(result.nickname()).thenReturn("leaf");
        OAuth2User loadUser = sut.load(oAuth2User);

        // then
        assertThat(loadUser.getAttributes()).containsKey("nickname");
        assertThat(loadUser.getAttributes()).containsKey("accessToken");
        assertThat(loadUser.getAttributes()).containsKey("provider");
        assertThat(loadUser.getAttributes()).extractingByKey("isFirstLogin", as(BOOLEAN)).isFalse();
    }

    @Test
    @DisplayName("DB에 존재하지 않는 계정은 회원가입, isFirstLogin = true")
    void testNotExistUser() {
        // given
        OAuth2User user = mock();
        Map<String, Object> attributes = Map.of("id", "sns_id",
                "kakao_account", new OAuth2Dto.KakaoUserInfo.KakaoAccount(null, true, true, true,
                        new OAuth2Dto.KakaoUserInfo.KakaoAccount.Profile("leaf", true)));

        // when
        when(user.getAttributes()).thenReturn(attributes);
        when(loadSnsAccountUseCase.loadSnsAccount(any(), any())).thenReturn(null);
        when(createSnsAccountUseCase.createSnsAccount(any(), any(), any(), any(), any())).thenReturn(MockEntityFactoryService.getSnsAccount());
        OAuth2User loadUser = sut.load(user);

        // then
        assertThat(loadUser.getAttributes()).extractingByKey("nickname").isEqualTo("leaf");
        assertThat(loadUser.getAttributes()).extractingByKey("isFirstLogin", as(BOOLEAN)).isTrue();
    }

    @Test
    @DisplayName("DB에 존재하지만 사용자와 연동되지 않은 계정은 isFirstLogin = true")
    void testNotRegisteredSnsAccount() {
        // given
        OAuth2User user = mock(OAuth2User.class);
        SnsAccount snsAccount = MockEntityFactoryService.getSnsAccount();

        // when
        when(loadSnsAccountUseCase.loadSnsAccount(any(), any())).thenReturn(snsAccount);
        OAuth2User loadUser = sut.load(user);

        // then
        assertThat(loadUser.getAttributes()).extractingByKey("isFirstLogin", as(BOOLEAN)).isTrue();
    }
}