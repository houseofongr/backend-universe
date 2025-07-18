package com.hoo.aar.adapter.out.api;

import com.hoo.common.adapter.out.persistence.repository.SnsAccountJpaRepository;
import com.hoo.admin.domain.user.snsaccount.SnsDomain;
import com.hoo.common.adapter.out.persistence.entity.SnsAccountJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;


class KakaoLoginApiAdapterTest {

    KakaoLoginApiAdapter sut;

    SnsAccountJpaRepository snsAccountJpaRepository;
    KakaoLoginApi kakaoLoginApi;

    @BeforeEach
    void init() {
        snsAccountJpaRepository = mock();
        kakaoLoginApi = mock();
        sut = new KakaoLoginApiAdapter(snsAccountJpaRepository, kakaoLoginApi);
    }

    @Test
    @DisplayName("카카오 계정 연결 해제 테스트")
    void testUnlinkKakaoAccount() {
        // given
        Long noKakaoUserId = 11L;

        // when
        when(snsAccountJpaRepository.findAllByUserId(noKakaoUserId)).thenReturn(List.of());
        sut.unlink(noKakaoUserId);

        // then
        verify(kakaoLoginApi, times(0)).unlink(anyString());

        // given
        reset(kakaoLoginApi);
        Long userId = 10L;

        // when
        when(snsAccountJpaRepository.findAllByUserId(userId)).thenReturn(List.of(
                new SnsAccountJpaEntity(1L, null, null, null, "SNS_ID_2", SnsDomain.NAVER, null),
                new SnsAccountJpaEntity(2L, null, null, null, "SNS_ID", SnsDomain.KAKAO, null)
        ));
        sut.unlink(userId);

        // then
        verify(kakaoLoginApi, times(1)).unlink(anyString());
    }

}