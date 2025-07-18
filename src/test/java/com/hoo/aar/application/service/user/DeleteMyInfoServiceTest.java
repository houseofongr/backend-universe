package com.hoo.aar.application.service.user;

import com.hoo.aar.application.port.out.api.UnlinkKakaoLoginPort;
import com.hoo.admin.application.port.in.user.DeleteUserCommand;
import com.hoo.admin.application.port.in.user.DeleteUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class DeleteMyInfoServiceTest {

    DeleteMyInfoService sut;

    UnlinkKakaoLoginPort unlinkKakaoLoginPort;
    DeleteUserUseCase deleteUserUseCase;

    @BeforeEach
    void init() {
        unlinkKakaoLoginPort = mock();
        deleteUserUseCase = mock();
        sut = new DeleteMyInfoService(unlinkKakaoLoginPort, deleteUserUseCase);
    }

    @Test
    @DisplayName("본인계정 탈퇴 서비스 테스트")
    void testDeleteMyInfo() {
        // given
        Long userId = 10L;
        DeleteUserCommand command = new DeleteUserCommand(true, true);

        // when
        sut.deleteMyInfo(userId, command);

        // then
        verify(unlinkKakaoLoginPort, times(1)).unlink(userId);
        verify(deleteUserUseCase, times(1)).deleteUser(userId, command);
    }

}