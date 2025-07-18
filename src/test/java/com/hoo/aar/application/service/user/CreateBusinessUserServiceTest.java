package com.hoo.aar.application.service.user;

import com.hoo.aar.application.port.in.user.CreateBusinessUserCommand;
import com.hoo.aar.application.port.in.user.CreateBusinessUserResult;
import com.hoo.aar.application.port.out.cache.LoadEmailAuthnStatePort;
import com.hoo.aar.application.port.out.persistence.user.SaveBusinessUserPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CreateBusinessUserServiceTest {

    LoadEmailAuthnStatePort loadEmailAuthnStatePort = mock();
    PasswordEncoder passwordEncoder = mock();
    SaveBusinessUserPort saveBusinessUserPort = mock();

    CreateBusinessUserService sut = new CreateBusinessUserService(loadEmailAuthnStatePort, passwordEncoder, saveBusinessUserPort);

    @Test
    @DisplayName("임시 비즈니스 회원 생성 서비스")
    void createTempBusinessUserService() {
        // given
        CreateBusinessUserCommand command = new CreateBusinessUserCommand("test@example.com", "test2143@", "temp_user123", true, true);

        // when
        when(loadEmailAuthnStatePort.loadAuthenticated(command.email())).thenReturn(true);
        when(saveBusinessUserPort.save(anyString(), any(), anyString(), any(), any())).thenReturn(1L);
        CreateBusinessUserResult result = sut.create(command);

        // then
        assertThat(result.message()).matches("\\[#\\d+]번 임시 사용자가 생성되었습니다. 관리자 승인 후 계정이 등록됩니다.");
        assertThat(result.tempUserId()).isEqualTo(1L);
        assertThat(result.email()).isEqualTo(command.email());
        assertThat(result.nickname()).isEqualTo(command.nickname());
    }

}