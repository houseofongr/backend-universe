package com.hoo.admin.adapter.out.persistence;

import com.hoo.aar.adapter.out.persistence.mapper.SnsAccountMapper;
import com.hoo.common.adapter.out.persistence.repository.SnsAccountJpaRepository;
import com.hoo.admin.application.port.out.snsaccount.FindSnsAccountPort;
import com.hoo.admin.application.port.out.snsaccount.SaveSnsAccountPort;
import com.hoo.admin.domain.user.snsaccount.SnsAccount;
import com.hoo.admin.domain.user.snsaccount.SnsDomain;
import com.hoo.common.adapter.out.persistence.entity.SnsAccountJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SnsAccountPersistenceAdapter implements FindSnsAccountPort, SaveSnsAccountPort {

    private final SnsAccountJpaRepository snsAccountJpaRepository;
    private final SnsAccountMapper snsAccountMapper;

    @Override
    public Optional<SnsAccount> loadSnsAccount(SnsDomain domain, String snsId) {
        return snsAccountJpaRepository.findWithUserEntity(domain, snsId)
                .map(snsAccountMapper::mapToDomainEntity);
    }

    @Override
    public Optional<SnsAccount> loadSnsAccount(Long id) {
        return snsAccountJpaRepository.findById(id)
                .map(snsAccountMapper::mapToDomainEntity);
    }

    @Override
    public void save(SnsAccount snsAccount) {
        SnsAccountJpaEntity newSnsAccountJpaEntity = SnsAccountJpaEntity.create(snsAccount);
        snsAccountJpaRepository.save(newSnsAccountJpaEntity);
    }

}
