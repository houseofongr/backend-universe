package com.hoo.universe.adapter.out.persistence.command;

import com.hoo.common.enums.AccessLevel;
import com.hoo.universe.adapter.out.persistence.PersistenceAdapterTest;
import com.hoo.universe.adapter.out.persistence.UniverseMapper;
import com.hoo.universe.adapter.out.persistence.entity.UniverseJpaEntity;
import com.hoo.universe.adapter.out.persistence.repository.CategoryJpaRepository;
import com.hoo.universe.adapter.out.persistence.repository.UniverseJpaRepository;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.UniverseCreateEvent;
import com.hoo.universe.domain.event.UniverseDeleteEvent;
import com.hoo.universe.domain.event.UniverseFileOverwriteEvent;
import com.hoo.universe.domain.event.UniverseMetadataUpdateEvent;
import com.hoo.universe.domain.vo.Category;
import com.hoo.universe.domain.vo.UniverseMetadata;
import com.hoo.universe.test.domain.UniverseTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverseOnly;
import static org.assertj.core.api.Assertions.assertThat;

@Sql("classpath:sql/universe.sql")
@PersistenceAdapterTest
class UniverseEventHandlerAdapterTest {

    @Autowired
    UniverseEventHandlerAdapter sut;

    @Autowired
    CategoryJpaRepository categoryJpaRepository;

    @Autowired
    UniverseJpaRepository universeJpaRepository;

    @Autowired
    private UniverseMapper universeMapper;

    @Test
    @DisplayName("유니버스 생성 이벤트 처리")
    void handleUniverseCreateEvent() {
        // given
        Category firstCategory = universeMapper.mapToUniverseCategory(categoryJpaRepository.findAll().getFirst());
        UniverseCreateEvent event = new UniverseCreateEvent(defaultUniverseOnly()
                .withCategory(firstCategory)
                .withUniverseMetadata(
                        UniverseMetadata.create(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), AccessLevel.PUBLIC,
                                List.of("유", "니", "버스"))
                )
                .build());

        // when
        sut.handleCreateUniverseEvent(event);
        UniverseJpaEntity newUniverseJpeEntity = universeJpaRepository.findByUuid(event.newUniverse().getId().uuid()).orElseThrow();

        // then
        assertThat(newUniverseJpeEntity.getUuid()).isEqualTo(event.newUniverse().getId().uuid());
        assertThat(newUniverseJpeEntity.getUniverseHashtags()).hasSize(3);
    }

    @Test
    @DisplayName("유니버스 상세정보 수정 이벤트 처리")
    void handleUniverseMetadataUpdateEvent() {
        // given
        Universe universe = getUniverseBuilder(1L).build();
        String title = "수정";

        UniverseMetadataUpdateEvent event = universe.updateMetadata(null, null, title, null, null, null);

        // when
        sut.handleUniverseMetadataUpdateEvent(event);
        UniverseJpaEntity newUniverseJpeEntity = findUniverseJpaEntity(universe);

        // then
        assertThat(newUniverseJpeEntity.getCommonMetadata().getTitle()).isEqualTo("수정");
    }

    @Test
    @DisplayName("유니버스 파일 덮어쓰기 이벤트 처리")
    void handleUniverseFileOverwriteEvent() {
        // given
        Universe universe = getUniverseBuilder(1L).build();
        UUID newInnerImageID = UUID.randomUUID();
        UniverseFileOverwriteEvent event = universe.overwriteFiles(null, null, newInnerImageID);

        // when
        sut.handleUniverseFileOverwriteEvent(event);
        UniverseJpaEntity newUniverseJpeEntity = findUniverseJpaEntity(universe);

        // then
        assertThat(newUniverseJpeEntity.getUniverseMetadata().getBackgroundFileID()).isEqualTo(newInnerImageID);
        assertThat(newUniverseJpeEntity.getUniverseMetadata().getThumbmusicFileID().toString()).isEqualTo("11111111-0000-0000-0000-000000000001");
        assertThat(newUniverseJpeEntity.getUniverseMetadata().getThumbnailFileID().toString()).isEqualTo("22222222-0000-0000-0000-000000000001");
    }

    @Test
    @DisplayName("유니버스 삭제 이벤트 처리")
    void handleUniverseDeleteEvent() {
        // given
        Universe universe = getUniverseBuilder(1L).build();
        UniverseDeleteEvent event = universe.delete();

        // when
        sut.handleUniverseDeleteEvent(event);

        // then
        assertThat(universeJpaRepository.findByUuid(universe.getId().uuid())).isEmpty();
    }

    private UniverseTestData.UniverseBuilder getUniverseBuilder(Long sqlID) {
        UUID universeID = universeJpaRepository.findUuidById(sqlID);
        return defaultUniverseOnly().withUniverseID(new Universe.UniverseID(universeID));
    }

    private UniverseJpaEntity findUniverseJpaEntity(Universe universe) {
        return universeJpaRepository.findByUuid(universe.getId().uuid()).orElseThrow();
    }
}