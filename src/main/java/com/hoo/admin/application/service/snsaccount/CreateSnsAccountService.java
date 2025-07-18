package com.hoo.admin.application.service.snsaccount;

import com.hoo.admin.application.port.in.snsaccount.CreateSnsAccountUseCase;
import com.hoo.admin.application.port.out.snsaccount.CreateSnsAccountPort;
import com.hoo.admin.application.port.out.snsaccount.SaveSnsAccountPort;
import com.hoo.admin.domain.user.snsaccount.SnsAccount;
import com.hoo.admin.domain.user.snsaccount.SnsDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateSnsAccountService implements CreateSnsAccountUseCase {

    private final CreateSnsAccountPort createSnsAccountPort;
    private final SaveSnsAccountPort saveSnsAccountPort;

    @Override
    public SnsAccount createSnsAccount(SnsDomain domain, String snsId, String realName, String nickname, String email) {

        SnsAccount newAccount = createSnsAccountPort.createSnsAccount(SnsDomain.KAKAO, snsId, realName, nickname, email);
        saveSnsAccountPort.save(newAccount);

        return newAccount;
    }

}
