package com.hoo.admin.application.service.home;

import com.hoo.admin.application.port.in.home.QueryHomeResult;
import com.hoo.admin.application.port.in.home.QueryUserHomesResult;
import com.hoo.admin.application.port.out.home.FindHomePort;
import com.hoo.admin.application.port.out.house.FindHousePort;
import com.hoo.admin.application.port.out.user.FindUserPort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class QueryHomeServiceTest {

    QueryHomeService sut;

    FindHomePort findHomePort;
    FindHousePort findHousePort;
    FindUserPort findUserPort;

    @BeforeEach
    void init() {
        findHomePort = mock();
        findHousePort = mock();
        findUserPort = mock();
        sut = new QueryHomeService(findHomePort, findHousePort, findUserPort);
    }

    @Test
    @DisplayName("홈 조회 서비스 테스트")
    void testQueryHome() throws Exception {
        // given
        Long id = 1L;

        // when
        when(findHomePort.loadHome(1L)).thenReturn(Optional.of(MockEntityFactoryService.loadHome()));
        when(findHousePort.load(anyLong())).thenReturn(Optional.of(MockEntityFactoryService.loadHouse()));
        when(findUserPort.loadUser(anyLong())).thenReturn(Optional.of(MockEntityFactoryService.getUser()));
        QueryHomeResult queryHomeResult = sut.queryHome(id);

        // then
        assertThat(queryHomeResult).isNotNull();
        assertThatThrownBy(() -> sut.queryHome(2L)).hasMessage(AdminErrorCode.HOME_NOT_FOUND.getMessage());
    }


    @Test
    @DisplayName("사용자 홈 리스트 조회 서비스 테스트")
    void testQueryUserHomes() throws Exception {
        // given
        Long id = 1L;

        // when
        when(findHomePort.loadHome(1L)).thenReturn(Optional.of(MockEntityFactoryService.loadHome()));
        when(findHousePort.load(anyLong())).thenReturn(Optional.of(MockEntityFactoryService.loadHouse()));
        when(findUserPort.loadUser(anyLong())).thenReturn(Optional.of(MockEntityFactoryService.getUser()));
        QueryUserHomesResult queryUserHomesResult = sut.queryUserHomes(id);

        // then
        assertThat(queryUserHomesResult).isNotNull();
    }

}