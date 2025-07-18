package com.hoo.common.adapter.out.persistence.entity;

import java.util.List;

public record TraversalJpaEntityComponents(
        UniverseJpaEntity universeJpaEntity,
        List<SpaceJpaEntity> spaceJpaEntities,
        List<PieceJpaEntity> pieceJpaEntities
) {

}
