package com.hoo.aar.application.service.authn;

import com.hoo.aar.application.port.in.authn.LoginBusinessUserResult;
import com.hoo.aar.application.port.in.authn.LoginBusinessUserUseCase;
import com.hoo.aar.application.port.out.jwt.IssueAccessTokenPort;
import com.hoo.aar.application.port.out.persistence.user.BusinessUserInfo;
import com.hoo.aar.application.port.out.persistence.user.FindBusinessUserPort;
import com.hoo.aar.application.service.AarErrorCode;
import com.hoo.aar.application.service.AarException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginBusinessUserService implements LoginBusinessUserUseCase {

    private final PasswordEncoder passwordEncoder;
    private final FindBusinessUserPort findBusinessUserPort;
    private final IssueAccessTokenPort issueAccessTokenPort;

    @Override
    public LoginBusinessUserResult login(String email, String password) {
        BusinessUserInfo businessUserInfo = findBusinessUserPort.findBusinessUserInfo(email);

        if (businessUserInfo.businessUserId() == null) throw new AarException(AarErrorCode.BUSINESS_USER_NOT_CONFIRMED);
        if (!passwordEncoder.matches(password, businessUserInfo.password())) throw new AarException(AarErrorCode.BUSINESS_LOGIN_FAILED);

        String accessToken = issueAccessTokenPort.issueAccessToken(businessUserInfo);

        return new LoginBusinessUserResult(
                String.format("[#%d]번 사용자가 로그인 되었습니다.", businessUserInfo.userId()),
                businessUserInfo.userId(),
                businessUserInfo.email(),
                businessUserInfo.nickname(),
                accessToken
        );
    }
}
