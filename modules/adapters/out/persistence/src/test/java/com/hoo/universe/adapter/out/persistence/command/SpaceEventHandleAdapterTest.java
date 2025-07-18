package com.hoo.universe.adapter.out.persistence.command;

import com.hoo.universe.adapter.out.persistence.PersistenceAdapterTest;
import com.hoo.universe.adapter.out.persistence.entity.SpaceJpaEntity;
import com.hoo.universe.adapter.out.persistence.query.UniverseLoadAdapter;
import com.hoo.universe.adapter.out.persistence.repository.SpaceJpaRepository;
import com.hoo.universe.adapter.out.persistence.repository.UniverseJpaRepository;
import com.hoo.universe.domain.Space;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.space.*;
import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.Outline;
import com.hoo.universe.domain.vo.SpaceMetadata;
import com.hoo.universe.test.domain.SpaceTestData;
import com.hoo.universe.test.domain.UniverseTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverseOnly;
import static org.assertj.core.api.Assertions.assertThat;

@Sql("classpath:sql/universe.sql")
@PersistenceAdapterTest
class SpaceEventHandleAdapterTest {

    @Autowired
    SpaceEventHandleAdapter sut;

    @Autowired
    UniverseJpaRepository universeJpaRepository;

    @Autowired
    SpaceJpaRepository spaceJpaRepository;

    @Autowired
    UniverseLoadAdapter universeLoadAdapter;

    @Test
    @DisplayName("스페이스 생성 이벤트 처리")
    void handleSpaceCreateEvent() {
        // given
        Universe universe = getUniverseBuilder(1L).build();
        SpaceCreateEvent event = universe.createSpaceInside(
                new Space.SpaceID(UUID.randomUUID()),
                null,
                SpaceMetadata.create(
                        UUID.randomUUID(),
                        false),
                CommonMetadata.create("공간", "스페이스는 공간입니다."),
                Outline.getRectangleBy2Point(0.9, 0.9, 0.95, 0.95)
        );

        // when
        sut.handleSpaceCreateEvent(event);
        SpaceJpaEntity spaceJpaEntity = findSpaceJpaEntity(event.newSpace());

        // then
        assertThat(spaceJpaEntity.getUuid()).isEqualTo(event.newSpace().getId().uuid());
    }

    @Test
    @DisplayName("스페이스 상세정보 수정 이벤트 처리")
    void handleSpaceMetadataUpdateEvent() {
        // given
        Space space = getSpaceBuilder(1L).build();
        SpaceMetadataUpdateEvent event = space.updateMetadata("수정", null, true);

        // when
        sut.handleSpaceMetadataUpdateEvent(event);
        SpaceJpaEntity spaceJpaEntity = findSpaceJpaEntity(space);

        // then
        assertThat(spaceJpaEntity.getCommonMetadata().getTitle()).isEqualTo("수정");
        assertThat(spaceJpaEntity.getCommonMetadata().getDescription()).isEqualTo("스페이스는 공간입니다.");
        assertThat(spaceJpaEntity.getSpaceMetadata().getHidden()).isTrue();
    }

    @Test
    @DisplayName("스페이스 파일 덮어쓰기 이벤트 처리")
    void handleSpaceFileOverwriteEvent() {
        // given
        Space space = getSpaceBuilder(1L).build();
        UUID newInnerImageID = UUID.randomUUID();
        SpaceFileOverwriteEvent event = space.overwriteFile(newInnerImageID);

        // when
        sut.handleSpaceFileOverwriteEvent(event);
        SpaceJpaEntity spaceJpaEntity = findSpaceJpaEntity(space);

        // then
        assertThat(spaceJpaEntity.getSpaceMetadata().getBackgroundFileID()).isEqualTo(newInnerImageID);
    }

    @Test
    @DisplayName("스페이스 이동 이벤트 처리")
    void handleSpaceMoveEvent() {
        // given
        UUID uuid = universeJpaRepository.findUuidById(1L);
        Universe universe = universeLoadAdapter.loadUniverseWithAllEntity(uuid);
        Outline rectangle = Outline.getRectangleBy2Point(0.05, 0.05, 0.1, 0.1);
        SpaceMoveEvent event = universe.moveSpace(universe.getSpaces().getLast().getSpaces().getFirst().getId(), rectangle);

        // when
        sut.handleSpaceMoveEvent(event);
        SpaceJpaEntity spaceJpaEntity = spaceJpaRepository.findByUuid(event.spaceID().uuid()).orElseThrow();

        // then
        assertThat(spaceJpaEntity.getOutlinePoints()).isEqualTo("[[0.05,0.05],[0.05,0.1],[0.1,0.1],[0.1,0.05]]");
    }

    @Test
    @DisplayName("스페이스 삭제 이벤트 처리")
    void handleSpaceDeleteEvent() {
        // given
        UUID uuid = universeJpaRepository.findUuidById(1L);
        Universe universe = universeLoadAdapter.loadUniverseWithAllEntity(uuid);
        Space space = universe.getSpaces().getLast();
        SpaceDeleteEvent event = space.delete();

        // when
        sut.handleSpaceDeleteEvent(event);

        // then
        assertThat(spaceJpaRepository.findByUuid(space.getId().uuid())).isEmpty();
    }

    private UniverseTestData.UniverseBuilder getUniverseBuilder(Long sqlID) {
        UUID universeID = universeJpaRepository.findUuidById(sqlID);
        return defaultUniverseOnly().withUniverseID(new Universe.UniverseID(universeID));
    }

    private SpaceTestData.SpaceBuilder getSpaceBuilder(Long sqlID) {
        UUID spaceID = spaceJpaRepository.findUuidById(sqlID);
        return SpaceTestData.defaultSpace().withSpaceID(new Space.SpaceID(spaceID));
    }

    private SpaceJpaEntity findSpaceJpaEntity(Space space) {
        return spaceJpaRepository.findByUuid(space.getId().uuid()).orElseThrow();
    }
}