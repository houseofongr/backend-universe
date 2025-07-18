package com.hoo.universe.adapter.out.persistence.repository;

import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.common.web.dto.PageRequest;
import com.hoo.universe.adapter.out.persistence.entity.SoundJpaEntity;

import java.util.UUID;

public interface PieceQueryDslRepository {
    PageQueryResult<SoundJpaEntity> searchAllSoundsById(UUID pieceID, PageRequest pageRequest);
}
