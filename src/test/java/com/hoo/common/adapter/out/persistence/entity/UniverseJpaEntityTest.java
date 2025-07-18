package com.hoo.common.adapter.out.persistence.entity;

import com.hoo.admin.domain.universe.Universe;
import com.hoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UniverseJpaEntityTest {

    @Test
    @DisplayName("유니버스 도메인으로부터 엔티티 생성")
    void testCreateEntityByDomain() {
        // given
        Universe universe = MockEntityFactoryService.getUniverse();

        // when
        UniverseJpaEntity universeJpaEntity = UniverseJpaEntity.create(universe, null, null);

        // then
        assertThat(universeJpaEntity.getId()).isEqualTo(null);
        assertThat(universeJpaEntity.getTitle()).isEqualTo(universe.getBasicInfo().getTitle());
        assertThat(universeJpaEntity.getDescription()).isEqualTo(universe.getBasicInfo().getDescription());
        assertThat(universeJpaEntity.getPublicStatus()).isEqualTo(universe.getBasicInfo().getPublicStatus());
        assertThat(universeJpaEntity.getViewCount()).isEqualTo(0L);
        assertThat(universeJpaEntity.getUniverseHashtags()).isEmpty();
        assertThat(universeJpaEntity.getUniverseLikes()).isEqualTo(List.of());
        assertThat(universeJpaEntity.getThumbMusicFileId()).isEqualTo(universe.getFileInfo().getThumbMusicId());
        assertThat(universeJpaEntity.getThumbnailFileId()).isEqualTo(universe.getFileInfo().getThumbnailId());
        assertThat(universeJpaEntity.getInnerImageFileId()).isEqualTo(universe.getFileInfo().getImageId());
    }
}