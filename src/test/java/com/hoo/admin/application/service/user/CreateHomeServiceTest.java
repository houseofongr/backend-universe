package com.hoo.admin.application.service.user;

import com.hoo.admin.application.port.in.home.CreateHomeCommand;
import com.hoo.admin.application.port.in.home.CreateHomeResult;
import com.hoo.admin.application.port.out.home.CreateHomePort;
import com.hoo.admin.application.port.out.home.SaveHomePort;
import com.hoo.admin.application.port.out.house.FindHousePort;
import com.hoo.admin.application.port.out.user.FindUserPort;
import com.hoo.admin.application.service.home.CreateHomeService;
import com.hoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CreateHomeServiceTest {

    CreateHomeService sut;

    FindHousePort findHousePort;
    FindUserPort findUserPort;
    CreateHomePort createHomePort;
    SaveHomePort saveHomePort;

    @BeforeEach
    void init() {
        findHousePort = mock();
        findUserPort = mock();
        createHomePort = mock();
        saveHomePort = mock();
        sut = new CreateHomeService(findHousePort, findUserPort, createHomePort, saveHomePort);
    }

    @Test
    @DisplayName("홈 생성 서비스 테스트")
    void testCreateHome() throws Exception {
        // given
        CreateHomeCommand command = new CreateHomeCommand(10L, 20L);

        // when
        when(findHousePort.load(20L)).thenReturn(Optional.of(MockEntityFactoryService.getHouse()));
        when(findUserPort.loadUser(10L)).thenReturn(Optional.of(MockEntityFactoryService.getUser()));
        when(saveHomePort.save(any())).thenReturn(100L);
        when(createHomePort.createHome(any(), any())).thenReturn(MockEntityFactoryService.getHome());
        CreateHomeResult createHomeResult = sut.create(command);

        // then
        verify(saveHomePort, times(1)).save(any());
        assertThat(createHomeResult.createdHomeId()).isEqualTo(100L);
    }
}