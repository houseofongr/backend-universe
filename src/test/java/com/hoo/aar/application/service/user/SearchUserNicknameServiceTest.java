package com.hoo.aar.application.service.user;

import com.hoo.aar.application.port.in.user.SearchUserNicknameResult;
import com.hoo.aar.application.port.out.persistence.user.SearchUserPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SearchUserNicknameServiceTest {

    SearchUserPort searchUserPort = mock();

    SearchUserNicknameService sut = new SearchUserNicknameService(searchUserPort);

    @Test
    @DisplayName("유저 닉네임 검색 서비스")
    void testUserNicknameSearchService() {
        // given
        String nickname = "leaf";
        String nickname2 = "leaf2";

        // when
        when(searchUserPort.existUserByNickname(nickname)).thenReturn(true);
        when(searchUserPort.existUserByNickname(nickname2)).thenReturn(false);
        SearchUserNicknameResult result1 = sut.search(nickname);
        SearchUserNicknameResult result2 = sut.search(nickname2);

        // then
        assertThat(result1.exist()).isTrue();
        assertThat(result2.exist()).isFalse();
    }

}