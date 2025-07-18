package com.hoo.admin.adapter.out.persistence;

import com.hoo.admin.adapter.out.persistence.mapper.SpaceMapper;
import com.hoo.admin.application.port.in.space.CreateSpaceResult;
import com.hoo.admin.domain.universe.space.Space;
import com.hoo.common.adapter.out.persistence.PersistenceAdapterTest;
import com.hoo.common.adapter.out.persistence.entity.SpaceJpaEntity;
import com.hoo.common.adapter.out.persistence.repository.SpaceJpaRepository;
import com.hoo.common.application.service.MockEntityFactoryService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@PersistenceAdapterTest
@Sql("classpath:sql/universe.sql")
@Import({SpacePersistenceAdapter.class, SpaceMapper.class})
class SpacePersistenceAdapterTest {

    @Autowired
    SpacePersistenceAdapter sut;

    @Autowired
    SpaceJpaRepository spaceJpaRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("스페이스 저장하기")
    void testSaveSpace() {
        // given
        Space space = MockEntityFactoryService.getParentSpace();

        // when
        CreateSpaceResult result = sut.save(space);
        SpaceJpaEntity spaceJpaEntity = spaceJpaRepository.findById(result.spaceId()).orElseThrow();

        // then
        assertThat(spaceJpaEntity.getInnerImageFileId()).isEqualTo(space.getFileInfo().getImageId());
        assertThat(spaceJpaEntity.getUniverseId()).isEqualTo(space.getBasicInfo().getUniverseId());
        assertThat(spaceJpaEntity.getSx()).isEqualTo(space.getPosInfo().getSx());
        assertThat(spaceJpaEntity.getSy()).isEqualTo(space.getPosInfo().getSy());
        assertThat(spaceJpaEntity.getEx()).isEqualTo(space.getPosInfo().getEx());
        assertThat(spaceJpaEntity.getEy()).isEqualTo(space.getPosInfo().getEy());
        assertThat(spaceJpaEntity.getTitle()).isEqualTo(space.getBasicInfo().getTitle());
        assertThat(spaceJpaEntity.getDescription()).isEqualTo(space.getBasicInfo().getDescription());
    }

    @Test
    @DisplayName("스페이스 조회 테스트")
    void testFindSpace() {
        // given
        Long spaceId = 1L;

        // when
        Space space = sut.find(spaceId);

        // then
        assertThat(space.getId()).isEqualTo(spaceId);
        assertThat(space.getFileInfo().getImageId()).isEqualTo(37L);
        assertThat(space.getBasicInfo().getParentSpaceId()).isNull();
        assertThat(space.getBasicInfo().getUniverseId()).isNull();
        assertThat(space.getBasicInfo().getTitle()).isEqualTo("SPACE1");
        assertThat(space.getBasicInfo().getDescription()).isEqualTo("유니버스의 스페이스-1");
        assertThat(space.getPosInfo().getSx()).isEqualTo(0.5f);
        assertThat(space.getPosInfo().getSy()).isEqualTo(0.5f);
        assertThat(space.getPosInfo().getEx()).isEqualTo(0.7f);
        assertThat(space.getPosInfo().getEy()).isEqualTo(0.6f);
        assertThat(space.getDateInfo().getCreatedTime()).isEqualTo(ZonedDateTime.of(2025, 7, 9, 15, 0, 0, 0, ZoneOffset.UTC));
        assertThat(space.getDateInfo().getUpdatedTime()).isEqualTo(ZonedDateTime.of(2025, 7, 9, 15, 0, 0, 0, ZoneOffset.UTC));
    }

    @Test
    @DisplayName("유니버스 ID 조회 테스트")
    void testFindUniverseId() {
        // given
        Long spaceId = 1L;

        // when
        Long universeId = sut.findUniverseId(spaceId);

        // then
        assertThat(universeId).isEqualTo(1L);

    }

    @Test
    @DisplayName("스페이스 수정 테스트")
    void testUpdateSpace() {
        // given
        Space space = Space.create(1L, 1L, 1L, null, "평화", "피스는 평화입니다.", 0.1f, 0.2f, 0.3f, 0.4f, false);

        // when
        sut.update(space);

        em.flush();
        em.clear();

        Space spaceInDB = sut.find(space.getId());

        // then
        assertThat(spaceInDB.getId()).isEqualTo(space.getId());
        assertThat(spaceInDB.getFileInfo().getImageId()).isEqualTo(1L);
        assertThat(spaceInDB.getBasicInfo().getParentSpaceId()).isNull();
        assertThat(spaceInDB.getBasicInfo().getUniverseId()).isNull();
        assertThat(spaceInDB.getBasicInfo().getTitle()).isEqualTo("평화");
        assertThat(spaceInDB.getBasicInfo().getDescription()).isEqualTo("피스는 평화입니다.");
        assertThat(spaceInDB.getPosInfo().getSx()).isEqualTo(0.1f);
        assertThat(spaceInDB.getPosInfo().getSy()).isEqualTo(0.2f);
        assertThat(spaceInDB.getPosInfo().getEx()).isEqualTo(0.3f);
        assertThat(spaceInDB.getPosInfo().getEy()).isEqualTo(0.4f);
        assertThat(spaceInDB.getDateInfo().getCreatedTime()).isEqualTo(ZonedDateTime.of(2025, 7, 9, 15, 0, 0, 0, ZoneOffset.UTC));
        assertThat(spaceInDB.getDateInfo().getUpdatedTime()).isAfter(ZonedDateTime.of(2025, 7, 9, 15, 0, 0, 0, ZoneOffset.UTC));
    }

    @Test
    @DisplayName("스페이스 삭제 테스트")
    void testDeleteSpace() {
        // given
        List<Long> ids = List.of(1L);

        // when
        sut.deleteAll(ids);

        // then
        assertThat(spaceJpaRepository.findAll()).noneMatch(spaceJpaEntity -> spaceJpaEntity.getId().equals(1L));
    }
}