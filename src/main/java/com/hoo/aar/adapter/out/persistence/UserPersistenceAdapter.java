package com.hoo.aar.adapter.out.persistence;

import com.hoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aar.application.port.out.persistence.user.BusinessUserInfo;
import com.hoo.aar.application.port.out.persistence.user.FindBusinessUserPort;
import com.hoo.aar.application.port.out.persistence.user.SaveBusinessUserPort;
import com.hoo.aar.application.service.AarErrorCode;
import com.hoo.aar.application.service.AarException;
import com.hoo.common.adapter.out.persistence.entity.BusinessUserJpaEntity;
import com.hoo.common.adapter.out.persistence.repository.BusinessUserJpaRepository;
import com.hoo.common.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.aar.application.port.in.user.SearchMyInfoResult;
import com.hoo.aar.application.port.out.persistence.user.SearchUserPort;
import com.hoo.common.adapter.out.persistence.entity.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("AARUserPersistenceAdapter")
@RequiredArgsConstructor
public class UserPersistenceAdapter implements SearchUserPort, SaveBusinessUserPort, FindBusinessUserPort {

    private final UserJpaRepository userJpaRepository;
    private final BusinessUserJpaRepository businessUserJpaRepository;
    private final UserMapper userMapper;

    @Override
    public SearchMyInfoResult queryMyInfo(Long userId) {
        UserJpaEntity userJpaEntity = userJpaRepository.findById(userId).orElseThrow();
        Integer homeCount = userJpaRepository.countHomeCountById(userId);
        Integer soundSourceCount = userJpaRepository.countActiveSoundSourceCountById(userId);
        return userMapper.mapToQueryMyInfoResult(userJpaEntity, homeCount, soundSourceCount);
    }

    @Override
    public boolean existUserByNickname(String nickname) {
        return userJpaRepository.existsByNickname(nickname);
    }

    @Override
    public Long save(String email, String encryptedPassword, String nickname, Boolean termsOfUseAgreement, Boolean personalInformationAgreement) {
        BusinessUserJpaEntity savedEntity = businessUserJpaRepository.save(BusinessUserJpaEntity.create(email, encryptedPassword, nickname, termsOfUseAgreement, personalInformationAgreement));
        return savedEntity.getId();
    }

    @Override
    public BusinessUserInfo findBusinessUserInfo(String email) {
        BusinessUserJpaEntity businessUserJpaEntity = businessUserJpaRepository.findByEmail(email)
                .orElseThrow(() -> new AarException(AarErrorCode.BUSINESS_USER_NOT_FOUND));
        return userMapper.mapToBusinessUserInfo(businessUserJpaEntity);
    }
}
