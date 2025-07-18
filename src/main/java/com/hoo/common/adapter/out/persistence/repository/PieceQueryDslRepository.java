package com.hoo.common.adapter.out.persistence.repository;

import com.hoo.admin.application.port.in.piece.SearchPieceCommand;
import com.hoo.common.adapter.out.persistence.entity.SoundJpaEntity;
import org.springframework.data.domain.Page;

public interface PieceQueryDslRepository {
    Page<SoundJpaEntity> searchAll(SearchPieceCommand command);
}
