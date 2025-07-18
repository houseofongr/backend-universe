//package com.aoo.common.adapter.out.persistence.entity;
//
//import com.aoo.admin.domain.universe.space.Space;
//import com.aoo.common.application.service.MockEntityFactoryService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class SpaceJpaEntityTest {
//
//    @Test
//    @DisplayName("스페이스로부터 엔티티 정상 생성")
//    void createNewEntity() {
//        // given
//        Space space = MockEntityFactoryService.getParentSpace();
//        UniverseJpaEntity universeJpaEntity = UniverseJpaEntity.create(MockEntityFactoryService.getUniverse(), null);
//
//        // when
//        SpaceJpaEntity newSpaceEntity = SpaceJpaEntity.create(space, universeJpaEntity);
//
//        // then
//        assertThat(newSpaceEntity.getId()).isNull();
//        assertThat(newSpaceEntity.getInnerImageFileId()).isEqualTo(space.getFileInfo().getInnerImageId());
//        assertThat(newSpaceEntity.getTitle()).isEqualTo(space.getBasicInfo().getTitle());
//        assertThat(newSpaceEntity.getDescription()).isEqualTo(space.getBasicInfo().getDescription());
//        assertThat(newSpaceEntity.getDx()).isEqualTo(space.getPosInfo().getDx());
//        assertThat(newSpaceEntity.getDy()).isEqualTo(space.getPosInfo().getDy());
//        assertThat(newSpaceEntity.getScaleX()).isEqualTo(space.getPosInfo().getScaleX());
//        assertThat(newSpaceEntity.getScaleY()).isEqualTo(space.getPosInfo().getScaleY());
//        assertThat(newSpaceEntity.getCreatedTime()).isNull();
//        assertThat(newSpaceEntity.getUpdatedTime()).isNull();
/// /        assertThat(newSpaceEntity.getUniverse()).isEqualTo(universeJpaEntity);
/// /        assertThat(newSpaceEntity.getParent()).isNull();
/// /        assertThat(newSpaceEntity.getChildren()).isEmpty();
//    }
//
//    @Test
//    @DisplayName("하위 스페이스 엔티티 생성")
//    void testCreateChildSpace() {
//        // given
//        Space space = MockEntityFactoryService.getParentSpace();
//        UniverseJpaEntity universe = UniverseJpaEntity.create(MockEntityFactoryService.getUniverse(), null);
//        SpaceJpaEntity parent = SpaceJpaEntity.create(space, universe);
//
//        Space space2 = MockEntityFactoryService.getParentSpace();
//
//        // when
//        SpaceJpaEntity childSpaceEntity = SpaceJpaEntity.createChild(space2, parent);
//
//        // then
//        assertThat(childSpaceEntity.getId()).isNull();
//        assertThat(childSpaceEntity.getInnerImageFileId()).isEqualTo(space2.getFileInfo().getInnerImageId());
//        assertThat(childSpaceEntity.getTitle()).isEqualTo(space2.getBasicInfo().getTitle());
//        assertThat(childSpaceEntity.getDescription()).isEqualTo(space2.getBasicInfo().getDescription());
//        assertThat(childSpaceEntity.getDx()).isEqualTo(space2.getPosInfo().getDx());
//        assertThat(childSpaceEntity.getDy()).isEqualTo(space2.getPosInfo().getDy());
//        assertThat(childSpaceEntity.getScaleX()).isEqualTo(space2.getPosInfo().getScaleX());
//        assertThat(childSpaceEntity.getScaleY()).isEqualTo(space2.getPosInfo().getScaleY());
//        assertThat(childSpaceEntity.getCreatedTime()).isNull();
//        assertThat(childSpaceEntity.getUpdatedTime()).isNull();
////        assertThat(childSpaceEntity.getUniverse()).isEqualTo(universe);
////        assertThat(childSpaceEntity.getParent()).isEqualTo(parent);
////        assertThat(childSpaceEntity.getChildren()).isEmpty();
////        assertThat(parent.getChildren()).contains(childSpaceEntity);
//    }
//}