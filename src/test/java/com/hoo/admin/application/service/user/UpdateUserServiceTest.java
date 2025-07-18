package com.hoo.admin.application.service.user;

import com.hoo.admin.application.port.in.user.UpdateUserInfoCommand;
import com.hoo.admin.application.port.out.user.FindUserPort;
import com.hoo.admin.application.port.out.user.UpdateUserPort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.domain.user.User;
import com.hoo.common.application.port.in.MessageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateUserServiceTest {

    UpdateUserService sut;

    FindUserPort findUserPort;
    UpdateUserPort updateUserPort;

    @BeforeEach
    void init() {
        findUserPort = mock();
        updateUserPort = mock();
        sut = new UpdateUserService(findUserPort, updateUserPort);
    }

    @Test
    @DisplayName("사용자 정보 수정 서비스 테스트")
    void testUpdateUserInfoTest() {
        // given
        Long userId = 10L;
        UpdateUserInfoCommand command = new UpdateUserInfoCommand("leaf2");
        User user = mock();

        // when
        when(findUserPort.loadUser(userId)).thenReturn(Optional.of(user));
        MessageDto messageDto = sut.updateUserInfo(userId, command);

        // then
        assertThatThrownBy(() -> sut.updateUserInfo(1234L, command)).hasMessage(AdminErrorCode.USER_NOT_FOUND.getMessage());
        verify(user, times(1)).updateNickname(any());
        verify(updateUserPort, times(1)).updateUser(any());
        assertThat(messageDto.message()).isEqualTo("본인정보가 수정되었습니다.");
    }

}