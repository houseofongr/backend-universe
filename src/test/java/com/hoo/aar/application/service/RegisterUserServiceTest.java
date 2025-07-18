package com.hoo.aar.application.service;

import com.hoo.aar.adapter.out.jwt.JwtAdapter;
import com.hoo.admin.application.port.in.user.RegisterUserCommand;
import com.hoo.admin.application.port.in.user.RegisterUserResult;
import com.hoo.admin.application.port.out.snsaccount.FindSnsAccountPort;
import com.hoo.admin.application.port.out.user.CreateUserPort;
import com.hoo.admin.application.port.out.user.SaveUserPort;
import com.hoo.admin.application.service.user.RegisterUserService;
import com.hoo.admin.domain.user.snsaccount.SnsAccount;
import com.hoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RegisterUserServiceTest {

    RegisterUserService sut;
    FindSnsAccountPort findSnsAccountPort;
    CreateUserPort createUserPort;
    SaveUserPort saveUserPort;
    JwtAdapter jwtAdapter;

    @BeforeEach
    void init() {
        findSnsAccountPort = mock();
        createUserPort = mock();
        saveUserPort = mock();
        jwtAdapter = mock();
        sut = new RegisterUserService(findSnsAccountPort, createUserPort, saveUserPort, jwtAdapter);
    }

    @Test
    @DisplayName("사용자 등록 테스트")
    void testRegisterUser() {
        // given
        RegisterUserCommand command = new RegisterUserCommand(1L, true, true);

        // when
        when(findSnsAccountPort.loadSnsAccount(1L)).thenReturn(Optional.of(MockEntityFactoryService.getSnsAccount()));
        when(createUserPort.createUser(any(), any(), any())).thenReturn(MockEntityFactoryService.getUser());
        RegisterUserResult register = sut.register(command);

        // then
        verify(findSnsAccountPort, times(1)).loadSnsAccount(1L);
        verify(saveUserPort, times(1)).save(any());
        verify(jwtAdapter, times(1)).issueAccessToken((SnsAccount) any());

        assertThat(register.nickname()).isEqualTo("leaf");
    }
}