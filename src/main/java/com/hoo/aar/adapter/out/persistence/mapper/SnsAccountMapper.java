package com.hoo.aar.adapter.out.persistence.mapper;

import com.hoo.admin.domain.user.snsaccount.SnsAccount;
import com.hoo.common.adapter.out.persistence.entity.SnsAccountJpaEntity;
import org.springframework.stereotype.Component;

@Component("AARSnsAccountMapper")
public class SnsAccountMapper {

    public SnsAccount mapToDomainEntity(SnsAccountJpaEntity snsAccountJpaEntity) {
        return SnsAccount.load(
                snsAccountJpaEntity.getId(),
                snsAccountJpaEntity.getSnsDomain(),
                snsAccountJpaEntity.getSnsId(),
                snsAccountJpaEntity.getRealName(),
                snsAccountJpaEntity.getNickname(),
                snsAccountJpaEntity.getEmail(),
                snsAccountJpaEntity.getCreatedTime(),
                snsAccountJpaEntity.getUpdatedTime(),
                snsAccountJpaEntity.getUserEntity() == null ? null : snsAccountJpaEntity.getUserEntity().getId()
        );
    }
}
