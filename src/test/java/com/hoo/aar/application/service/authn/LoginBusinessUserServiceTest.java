package com.hoo.aar.application.service.authn;

import com.hoo.aar.application.port.in.authn.LoginBusinessUserResult;
import com.hoo.aar.application.port.out.jwt.IssueAccessTokenPort;
import com.hoo.aar.application.port.out.persistence.user.BusinessUserInfo;
import com.hoo.aar.application.port.out.persistence.user.FindBusinessUserPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class LoginBusinessUserServiceTest {

    PasswordEncoder passwordEncoder = mock();
    FindBusinessUserPort findBusinessUserPort = mock();
    IssueAccessTokenPort issueAccessTokenPort = mock();

    LoginBusinessUserService sut = new LoginBusinessUserService(passwordEncoder, findBusinessUserPort, issueAccessTokenPort);

    @Test
    @DisplayName("비즈니스 사용자 로그인 서비스")
    void testLoginBusinessUser() {
        // given
        String email = "test@example.com";
        String password = "test2143@";
        BusinessUserInfo businessUserInfo = new BusinessUserInfo(1L, 1L, email, password, "test_user123");

        // when
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(findBusinessUserPort.findBusinessUserInfo(email)).thenReturn(businessUserInfo);
        when(issueAccessTokenPort.issueAccessToken(businessUserInfo)).thenReturn("access_token");
        LoginBusinessUserResult result = sut.login(email, password);

        // then
        assertThat(result.message()).matches("\\[#\\d+]번 사용자가 로그인 되었습니다.");
        assertThat(result).usingRecursiveComparison().ignoringFields(
                "message", "accessToken"
        ).isEqualTo(businessUserInfo);
        assertThat(result.accessToken()).isEqualTo("access_token");
    }

}