package com.hoo.admin.domain.universe;

import com.hoo.admin.domain.user.User;
import com.hoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UniverseTest {

    @Test
    @DisplayName("유니버스 생성")
    void testCreateUniverse() {
        // given
        String title = "우주";
        String description = "유니버스는 우주입니다.";
        List<String> tag = List.of("우주", "행성", "지구", "별");
        UniverseCategory category = new UniverseCategory(1L, "카테고리", "category");
        PublicStatus publicStatus = PublicStatus.PUBLIC;
        Long thumbMusicId = 100L;
        Long thumbnailId = 11L;
        Long innerImageId = 12L;
        User author = User.load(1L, "leaf");

        // when
        Universe universe = Universe.create(thumbMusicId, thumbnailId, innerImageId, title, description, category, publicStatus, tag, author);
        UniverseBasicInfo basicInfo = universe.getBasicInfo();
        SocialInfo socialInfo = universe.getSocialInfo();

        // then
        assertThat(universe.getId()).isNull();
        assertThat(universe.getFileInfo().getThumbnailId()).isEqualTo(thumbnailId);
        assertThat(universe.getFileInfo().getThumbMusicId()).isEqualTo(thumbMusicId);
        assertThat(universe.getFileInfo().getImageId()).isEqualTo(innerImageId);
        assertThat(universe.getDateInfo()).isNull();
        assertThat(universe.getTreeInfo()).isNull();
        assertThat(universe.getAuthorInfo().getId()).isEqualTo(author.getUserInfo().getId());
        assertThat(universe.getCategory()).isEqualTo(category);
        assertThat(basicInfo.getPublicStatus()).isEqualTo(publicStatus);
        assertThat(basicInfo.getTitle()).isEqualTo(title);
        assertThat(basicInfo.getDescription()).isEqualTo(description);
        assertThat(socialInfo.getHashtags()).hasSize(4);
        assertThat(socialInfo.getHashtags()).contains(tag.toArray(String[]::new));
        assertThat(socialInfo.getLikeCount()).isEqualTo(0L);
        assertThat(socialInfo.getViewCount()).isEqualTo(0L);
    }

    @Test
    @DisplayName("기본정보(제목, 내용, 카테고리, 노출여부) 수정하기")
    void testUpdateUniverseBasicInfo() {
        // given
        Universe universe = MockEntityFactoryService.getUniverse();

        String title = "오르트구름";
        String description = "오르트구름은 태양계 최외곽에 위치하고 있습니다.";
        PublicStatus publicStatus = PublicStatus.PRIVATE;

        // when
        universe.getBasicInfo().updateUniverseInfo(title, description,null);

        // then
        assertThat(universe.getBasicInfo().getTitle()).isEqualTo(title);
        assertThat(universe.getBasicInfo().getDescription()).isEqualTo(description);
        assertThat(universe.getBasicInfo().getPublicStatus()).isEqualTo(PublicStatus.PUBLIC);

        // when 2
        universe.getBasicInfo().updateUniverseInfo(null, null, publicStatus);

        // then 2
        assertThat(universe.getBasicInfo().getPublicStatus()).isEqualTo(publicStatus);
    }

    @Test
    @DisplayName("소셜정보(태그) 수정하기")
    void testUpdateSocialInfo() {
        // given
        Universe universe = MockEntityFactoryService.getUniverse();

        List<String> tags = List.of("오르트구름", "태양계", "윤하", "별");

        // when
        universe.getSocialInfo().updateHashtag(tags);

        // then
        assertThat(universe.getSocialInfo().getHashtags()).contains(tags.toArray(String[]::new));
    }

    @Test
    @DisplayName("썸네일 / 썸뮤직 / 내부이미지 수정하기")
    void testUpdateFiles() {
        // given
        Universe universe = MockEntityFactoryService.getUniverse();

        Long thumbMusicId = 101L;
        Long thumbnailId = 12L;
        Long innerImageId = 13L;

        // when
        universe.getFileInfo().updateThumbMusic(thumbMusicId);
        universe.getFileInfo().updateThumbnail(thumbnailId);
        universe.getFileInfo().updateImage(innerImageId);

        // then
        assertThat(universe.getFileInfo().getThumbnailId()).isEqualTo(thumbnailId);
        assertThat(universe.getFileInfo().getThumbMusicId()).isEqualTo(thumbMusicId);
        assertThat(universe.getFileInfo().getImageId()).isEqualTo(innerImageId);
    }
}