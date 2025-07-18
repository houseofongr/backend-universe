package com.hoo.universe.api.dto.result;

import com.hoo.universe.domain.Universe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverseOnly;
import static org.assertj.core.api.Assertions.assertThat;

class OpenUniverseResultTest {

    @Test
    @DisplayName("매핑")
    void mapping() {
        Universe universe = defaultUniverseOnly().build();

        OpenUniverseResult result = OpenUniverseResult.from(universe);

        assertThat(result.id()).isEqualTo(universe.getId().uuid());
        assertThat(result.thumbMusicID()).isEqualTo(universe.getUniverseMetadata().getThumbmusicID());
        assertThat(result.thumbnailID()).isEqualTo(universe.getUniverseMetadata().getThumbnailID());
        assertThat(result.backgroundID()).isEqualTo(universe.getUniverseMetadata().getBackgroundID());
        assertThat(result.ownerID()).isEqualTo(universe.getOwner().getId());

        assertThat(result.createdTime()).isEqualTo(universe.getCommonMetadata().getCreatedTime().toEpochSecond());
        assertThat(result.updatedTime()).isEqualTo(universe.getCommonMetadata().getUpdatedTime().toEpochSecond());

        assertThat(result.view()).isEqualTo(universe.getUniverseMetadata().getViewCount());
        assertThat(result.like()).isEqualTo(universe.getUniverseMetadata().getLikeCount());

        assertThat(result.title()).isEqualTo(universe.getCommonMetadata().getTitle());
        assertThat(result.description()).isEqualTo(universe.getCommonMetadata().getDescription());
        assertThat(result.owner()).isEqualTo(universe.getOwner().getNickname());
        assertThat(result.accessLevel()).isEqualTo(universe.getUniverseMetadata().getAccessLevel().name());

        assertThat(result.category()).isEqualTo(universe.getCategory());
        assertThat(result.hashtags()).isEqualTo(universe.getUniverseMetadata().getTags());

    }

}