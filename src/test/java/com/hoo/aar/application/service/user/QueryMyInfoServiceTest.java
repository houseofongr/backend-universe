package com.hoo.aar.application.service.user;

import com.hoo.aar.application.port.out.persistence.user.SearchUserPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class QueryMyInfoServiceTest {

    QueryMyInfoService sut;

    SearchUserPort searchUserPort;

    @BeforeEach
    void init() {
        searchUserPort = mock();
        sut = new QueryMyInfoService(searchUserPort);
    }

    @Test
    @DisplayName("본인정보 조회 서비스 테스트")
    void testQueryMyInfoService() {
        // given
        Long userId = 1L;

        // when
        sut.queryMyInfo(userId);

        // then
        verify(searchUserPort, times(1)).queryMyInfo(userId);
    }
}