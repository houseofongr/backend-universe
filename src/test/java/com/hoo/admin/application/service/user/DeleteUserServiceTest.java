package com.hoo.admin.application.service.user;

import com.hoo.admin.application.port.in.user.CreateDeletedUserPort;
import com.hoo.admin.application.port.in.user.DeleteUserCommand;
import com.hoo.admin.application.port.in.user.DeleteUserPort;
import com.hoo.admin.application.port.in.user.SaveDeletedUserPort;
import com.hoo.admin.application.port.out.user.FindUserPort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.common.application.port.in.MessageDto;
import com.hoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteUserServiceTest {

    DeleteUserService sut;

    FindUserPort findUserPort;
    CreateDeletedUserPort createDeletedUserPort;
    SaveDeletedUserPort saveDeletedUserPort;
    DeleteUserPort deleteUserPort;

    @BeforeEach
    void init() {
        findUserPort = mock();
        createDeletedUserPort = mock();
        saveDeletedUserPort = mock();
        deleteUserPort = mock();
        sut = new DeleteUserService(findUserPort, createDeletedUserPort, saveDeletedUserPort, deleteUserPort);
    }

    @Test
    @DisplayName("회원탈퇴 서비스 테스트")
    void testDeleteUser() {
        // given
        Long userId = 10L;
        DeleteUserCommand command = new DeleteUserCommand(true, true);

        // when
        when(findUserPort.loadUser(userId)).thenReturn(Optional.of(MockEntityFactoryService.getUser()));
        when(createDeletedUserPort.createDeletedUser(any(), anyBoolean(), anyBoolean())).thenReturn(MockEntityFactoryService.getDeletedUser());
        assertThatThrownBy(() -> sut.deleteUser(1234L, command)).hasMessage(AdminErrorCode.USER_NOT_FOUND.getMessage());
        MessageDto messageDto = sut.deleteUser(userId, command);

        // then
        verify(saveDeletedUserPort, times(1)).saveDeletedUser(any());
        verify(deleteUserPort, times(1)).deleteUser(any());
        assertThat(messageDto.message()).isEqualTo("회원탈퇴가 완료되었습니다.");
    }
}