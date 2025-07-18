package com.hoo.admin.application.service.user;

import com.hoo.admin.application.port.in.user.SearchUserCommand;
import com.hoo.admin.application.port.out.user.SearchUserPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import static org.mockito.Mockito.*;

class SearchUserServiceTest {

    SearchUserService sut;

    SearchUserPort searchUserPort;

    @BeforeEach
    void init() {
        searchUserPort = mock();
        sut = new SearchUserService(searchUserPort);
    }

    @Test
    @DisplayName("사용자 리스트 조회 서비스 테스트")
    void testQueryUserList() {
        // given
        SearchUserCommand command = new SearchUserCommand(PageRequest.of(1, 10), null, null, null, null);

        // when
        sut.query(command);

        // then
        verify(searchUserPort, times(1)).search(any());
    }

}