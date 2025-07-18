package com.hoo.admin.adapter.out.persistence.mapper;

import com.hoo.admin.application.port.in.space.CreateSpaceResult;
import com.hoo.admin.domain.universe.space.Space;
import com.hoo.common.adapter.out.persistence.entity.SpaceJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class SpaceMapper {

    public Space mapToSingleDomainEntity(SpaceJpaEntity spaceJpaEntity) {
        return Space.loadWithoutRelation(spaceJpaEntity.getId(),
                spaceJpaEntity.getInnerImageFileId(),
                spaceJpaEntity.getTitle(),
                spaceJpaEntity.getDescription(),
                spaceJpaEntity.getSx(),
                spaceJpaEntity.getSy(),
                spaceJpaEntity.getEx(),
                spaceJpaEntity.getEy(),
                spaceJpaEntity.getHidden(),
                spaceJpaEntity.getCreatedTime(),
                spaceJpaEntity.getUpdatedTime()
        );
    }

    public CreateSpaceResult mapToCreateSpaceResult(SpaceJpaEntity spaceJpaEntity) {
        return new CreateSpaceResult(
                String.format("[#%d]번 스페이스가 생성되었습니다.", spaceJpaEntity.getId()),
                spaceJpaEntity.getId(),
                spaceJpaEntity.getInnerImageFileId(),
                spaceJpaEntity.getTitle(),
                spaceJpaEntity.getDescription(),
                spaceJpaEntity.getSx(),
                spaceJpaEntity.getSy(),
                spaceJpaEntity.getEx(),
                spaceJpaEntity.getEy(),
                spaceJpaEntity.getHidden()
        );
    }
}
