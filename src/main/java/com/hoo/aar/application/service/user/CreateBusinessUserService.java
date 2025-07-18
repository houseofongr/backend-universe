package com.hoo.aar.application.service.user;

import com.hoo.aar.application.port.in.user.CreateBusinessUserCommand;
import com.hoo.aar.application.port.in.user.CreateBusinessUserResult;
import com.hoo.aar.application.port.in.user.CreateBusinessUserUseCase;
import com.hoo.aar.application.port.out.cache.LoadEmailAuthnStatePort;
import com.hoo.aar.application.port.out.persistence.user.SaveBusinessUserPort;
import com.hoo.aar.application.service.AarErrorCode;
import com.hoo.aar.application.service.AarException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateBusinessUserService implements CreateBusinessUserUseCase {

    private final LoadEmailAuthnStatePort loadEmailAuthnStatePort;
    private final PasswordEncoder passwordEncoder;
    private final SaveBusinessUserPort saveBusinessUserPort;

    @Override
    public CreateBusinessUserResult create(CreateBusinessUserCommand command) {
        if (!loadEmailAuthnStatePort.loadAuthenticated(command.email())) throw new AarException(AarErrorCode.NOT_VERIFIED_EMAIL);

        String encodedPassword = passwordEncoder.encode(command.password());
        Long tempUserId = saveBusinessUserPort.save(command.email(), encodedPassword, command.nickname(), command.termsOfUseAgreement(), command.personalInformationAgreement());

        return new CreateBusinessUserResult(
                String.format("[#%d]번 임시 사용자가 생성되었습니다. 관리자 승인 후 계정이 등록됩니다.", tempUserId),
                tempUserId,
                command.email(),
                command.nickname(),
                command.termsOfUseAgreement(),
                command.personalInformationAgreement()
        );
    }
}
