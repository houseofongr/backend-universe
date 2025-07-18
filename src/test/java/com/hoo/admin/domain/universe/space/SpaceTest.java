package com.hoo.admin.domain.universe.space;

import com.hoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SpaceTest {

    @Test
    @DisplayName("요청 정보(제목, 내용, 위치좌표(startX, startY), 크기(endX, endY), 유니버스id, 상위 스페이스, 내부사진id)로 스페이스 생성")
    void createSpace() {
        // given
        Long id = 1L;
        Long universeId = 12L;
        Long imageId = 100L;
        String title = "공간";
        String description = "스페이스는 공간입니다.";
        Float sx = 1000 / 5000F;
        Float sy = 2000 / 5000F;
        Float ex = 1500 / 5000F;
        Float ey = 1500 / 5000F;

        // when
        Space newSpace = Space.create(id, imageId, universeId, null, title, description, sx, sy, ex, ey, false);

        // then
        assertThat(newSpace.getId()).isEqualTo(id);
        assertThat(newSpace.getBasicInfo().getUniverseId()).isEqualTo(universeId);
        assertThat(newSpace.getFileInfo().getImageId()).isEqualTo(imageId);
        assertThat(newSpace.getBasicInfo().getTitle()).isEqualTo(title);
        assertThat(newSpace.getBasicInfo().getDescription()).isEqualTo(description);
        assertThat(newSpace.getPosInfo().getSx()).isEqualTo(sx);
        assertThat(newSpace.getPosInfo().getSy()).isEqualTo(sy);
        assertThat(newSpace.getPosInfo().getEx()).isEqualTo(ex);
        assertThat(newSpace.getPosInfo().getEy()).isEqualTo(ey);
    }

    @Test
    @DisplayName("기본정보(제목, 내용) 수정하기")
    void testUpdateBasicInfo() {
        // given
        String title = "블랙홀";
        String description = "블랙홀은 빛도 빨아들입니다.";
        Space space = MockEntityFactoryService.getParentSpace();

        // try 1
        space.getBasicInfo().update(null, null);
        assertThat(space.getBasicInfo().getTitle()).isEqualTo("공간");
        assertThat(space.getBasicInfo().getDescription()).isEmpty();

        // try 2
        space.getBasicInfo().update(title, null);
        assertThat(space.getBasicInfo().getTitle()).isEqualTo(title);
        assertThat(space.getBasicInfo().getDescription()).isEmpty();

        // try 3
        space.getBasicInfo().update(null, description);
        assertThat(space.getBasicInfo().getTitle()).isEqualTo(title);
        assertThat(space.getBasicInfo().getDescription()).isEqualTo(description);
    }

    @Test
    @DisplayName("위치정보(startX, startY, endX, endY) 수정하기")
    void testUpdatePosInfo() {
        // given
        Float sx = 0.1f;
        Float sy = 0.2f;
        Float ex = 0.3f;
        Float ey = 0.4f;
        Space space = MockEntityFactoryService.getParentSpace();

        // try 1
        space.getPosInfo().update(sx, sy, null, null);
        assertThat(space.getPosInfo().getSx()).isEqualTo(sx);
        assertThat(space.getPosInfo().getSy()).isEqualTo(sy);
        assertThat(space.getPosInfo().getEx()).isEqualTo(0.8f);
        assertThat(space.getPosInfo().getEy()).isEqualTo(0.7f);

        // try 2
        space.getPosInfo().update(null, null, ex, ey);
        assertThat(space.getPosInfo().getSx()).isEqualTo(sx);
        assertThat(space.getPosInfo().getSy()).isEqualTo(sy);
        assertThat(space.getPosInfo().getEx()).isEqualTo(ex);
        assertThat(space.getPosInfo().getEy()).isEqualTo(ey);
    }

    @Test
    @DisplayName("이미지 수정하기")
    void testUpdateInnerImageId() {
        // given
        Long imageId = 4321L;
        Space space = MockEntityFactoryService.getParentSpace();

        space.getFileInfo().updateImage(imageId);
        assertThat(space.getFileInfo().getImageId()).isEqualTo(imageId);
    }
}