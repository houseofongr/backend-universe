package com.hoo.admin.application.service.home;

import com.hoo.admin.application.port.out.home.FindHomePort;
import com.hoo.admin.application.port.out.home.UpdateHomePort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.domain.exception.BadHomeNameFormatException;
import com.hoo.admin.domain.home.Home;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class UpdateHomeServiceTest {

    UpdateHomeService sut;

    FindHomePort findHomePort;
    UpdateHomePort updateHomePort;

    @BeforeEach
    void init() {
        findHomePort = mock();
        updateHomePort = mock();
        sut = new UpdateHomeService(findHomePort, updateHomePort);
    }

    @Test
    @DisplayName("홈 수정 서비스 테스트")
    void testUpdateHomeNameService() {
        // given
        Long homeId = 1L;
        String homeName = "수정할 이름";
        String badName = "잘못된 이름";
        Home home = mock();

        // when
        when(findHomePort.loadHome(homeId)).thenReturn(Optional.of(home));
        doThrow(new BadHomeNameFormatException(badName)).when(home).updateName(badName);
        sut.updateHomeName(homeId, homeName);

        // then
        verify(updateHomePort, times(1)).updateHomeName(home);
        assertThatThrownBy(() -> sut.updateHomeName(homeId, badName)).hasMessage(AdminErrorCode.ILLEGAL_HOME_NAME_FORMAT.getMessage());
    }

    @Test
    @DisplayName("메인 홈 수정 테스트")
    void testUpdateMainHome() {
        // given
        Long userId = 1L;
        Long homeId = 1L;

        // when
        sut.updateMainHome(userId, homeId);

        // then
        verify(updateHomePort, times(1)).updateMainHome(userId, homeId);
    }

}