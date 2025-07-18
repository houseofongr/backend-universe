package com.hoo.admin.application.service.user;

import com.hoo.admin.application.port.in.user.ConfirmBusinessUserResult;
import com.hoo.admin.application.port.out.user.RegisterBusinessUserPort;
import com.hoo.admin.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ConfirmBusinessUserServiceTest {

    RegisterBusinessUserPort registerBusinessUserPort = mock();

    ConfirmBusinessUserService sut = new ConfirmBusinessUserService(registerBusinessUserPort);

    @Test
    @DisplayName("비즈니스 회원가입 승인 서비스")
    void testConfirmBusinessUserService() {
        // given
        Long tempUserId = 1L;
        User user = User.load(1L, null, "leaf", "test@example.com", true, true, ZonedDateTime.now(), ZonedDateTime.now(), List.of());

        // when
        when(registerBusinessUserPort.registerBusinessUser(tempUserId)).thenReturn(user);
        ConfirmBusinessUserResult result = sut.confirm(tempUserId);

        // then
        assertThat(result.message()).matches("비즈니스 사용자가 등록되어 \\[#\\d+]번 사용자가 생성되었습니다.");
        assertThat(result.userId()).isEqualTo(1L);
        assertThat(result.email()).isEqualTo("test@example.com");
        assertThat(result.nickname()).isEqualTo("leaf");
    }

}