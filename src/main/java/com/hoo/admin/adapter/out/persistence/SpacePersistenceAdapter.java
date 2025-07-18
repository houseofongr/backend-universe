package com.hoo.admin.adapter.out.persistence;

import com.hoo.admin.adapter.out.persistence.mapper.SpaceMapper;
import com.hoo.admin.application.port.in.space.CreateSpaceResult;
import com.hoo.admin.application.port.out.space.DeleteSpacePort;
import com.hoo.admin.application.port.out.space.FindSpacePort;
import com.hoo.admin.application.port.out.space.SaveSpacePort;
import com.hoo.admin.application.port.out.space.UpdateSpacePort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import com.hoo.admin.domain.universe.space.Space;
import com.hoo.common.adapter.out.persistence.entity.SpaceJpaEntity;
import com.hoo.common.adapter.out.persistence.repository.SpaceJpaRepository;
import com.hoo.common.adapter.out.persistence.repository.UniverseJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SpacePersistenceAdapter implements FindSpacePort, SaveSpacePort, UpdateSpacePort, DeleteSpacePort {

    private final UniverseJpaRepository universeJpaRepository;
    private final SpaceJpaRepository spaceJpaRepository;
    private final SpaceMapper spaceMapper;

    @Override
    public Space find(Long id) {
        SpaceJpaEntity spaceJpaEntity = spaceJpaRepository.findById(id).orElseThrow(() -> new AdminException(AdminErrorCode.SPACE_NOT_FOUND));
        return spaceMapper.mapToSingleDomainEntity(spaceJpaEntity);
    }

    @Override
    public Long findUniverseId(Long id) {
        SpaceJpaEntity spaceJpaEntity = spaceJpaRepository.findById(id).orElseThrow(() -> new AdminException(AdminErrorCode.SPACE_NOT_FOUND));
        return spaceJpaEntity.getUniverseId();
    }

    @Override
    public CreateSpaceResult save(Space space) {
        universeJpaRepository.findById(space.getBasicInfo().getUniverseId()).orElseThrow(() -> new AdminException(AdminErrorCode.UNIVERSE_NOT_FOUND));

        Long parentSpaceId = space.getBasicInfo().getParentSpaceId();
        if (parentSpaceId != null && parentSpaceId != -1)
            spaceJpaRepository.findById(parentSpaceId).orElseThrow(() -> new AdminException(AdminErrorCode.SPACE_NOT_FOUND));

        SpaceJpaEntity spaceJpaEntity = SpaceJpaEntity.create(space);

        spaceJpaRepository.save(spaceJpaEntity);

        return spaceMapper.mapToCreateSpaceResult(spaceJpaEntity);
    }

    @Override
    public void update(Space space) {
        SpaceJpaEntity spaceJpaEntity = spaceJpaRepository.findById(space.getId()).orElseThrow(() -> new AdminException(AdminErrorCode.SPACE_NOT_FOUND));
        spaceJpaEntity.update(space);
    }

    @Override
    public void deleteAll(List<Long> ids) {
        spaceJpaRepository.deleteAllById(ids);
    }
}
