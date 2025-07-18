package com.hoo.universe.adapter.out.persistence.repository;

import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.common.web.dto.PageRequest;
import com.hoo.universe.adapter.out.persistence.entity.UniverseJpaEntity;

import java.util.UUID;

public interface UniverseQueryDslRepository {
    PageQueryResult<UniverseJpaEntity> searchUniverse(UUID categoryID, PageRequest pageRequest);
//    Page<UniverseJpaEntity> searchAll(SearchUniverseCommand command);

//    Page<UniverseJpaEntity>  searchAllPublic(SearchPublicUniverseCommand command);

}
