package com.hoo.universe.application;

import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.universe.api.out.dto.OpenPieceQueryResult;
import com.hoo.universe.api.out.dto.UniverseListQueryInfo;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.test.domain.UniverseTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class FileOwnerMapExtractorTest {

    FileOwnerMapExtractor sut = new FileOwnerMapExtractor();

    @Test
    @DisplayName("유니버스로 파일 소유자 맵 추출")
    void extractByUniverse() {
        // given
        Universe universe = UniverseTestData.defaultUniverse();

        // when
        Map<UUID, UUID> result = sut.extractFileOwnerMap(universe);

        // then
        assertThat(result).containsKeys(
                universe.getUniverseMetadata().getThumbnailID(),
                universe.getUniverseMetadata().getThumbmusicID(),
                universe.getUniverseMetadata().getBackgroundID());
        result.values().forEach(value -> assertThat(value).isEqualTo(universe.getOwner().getId()));
    }

    @Test
    @DisplayName("PageQueryResult로 파일 소유자 맵 추출")
    void extractFileOwnerMap_fromQueryResult() {
        // given
        UUID fileId1 = UUID.randomUUID();
        UUID fileId2 = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();

        UniverseListQueryInfo info1 = mock();
        when(info1.extractFileOwnerMap()).thenReturn(Map.of(fileId1, ownerId));

        UniverseListQueryInfo info2 = mock();
        when(info2.extractFileOwnerMap()).thenReturn(Map.of(fileId2, ownerId));

        PageQueryResult<UniverseListQueryInfo> queryResult = new PageQueryResult<>(List.of(info1, info2), mock());

        // when
        Map<UUID, UUID> result = sut.extractFileOwnerMap(queryResult);

        // then
        assertThat(result).containsOnlyKeys(fileId1, fileId2);
        assertThat(result.get(fileId1)).isEqualTo(ownerId);
        assertThat(result.get(fileId2)).isEqualTo(ownerId);
    }

    @Test
    @DisplayName("OpenPieceQueryResult로 파일 소유자 맵 추출")
    void extractFileOwnerMap_fromOpenPieceQueryResult() {
        // given
        UUID ownerId = UUID.randomUUID();
        UUID audioId1 = UUID.randomUUID();
        UUID audioId2 = UUID.randomUUID();

        OpenPieceQueryResult.SoundInfo sound1 = mock(OpenPieceQueryResult.SoundInfo.class);
        when(sound1.audioFileID()).thenReturn(audioId1);
        OpenPieceQueryResult.SoundInfo sound2 = mock(OpenPieceQueryResult.SoundInfo.class);
        when(sound2.audioFileID()).thenReturn(audioId2);

        PageQueryResult<OpenPieceQueryResult.SoundInfo> sounds = new PageQueryResult<>(List.of(sound1, sound2), mock());

        OpenPieceQueryResult queryResult = mock(OpenPieceQueryResult.class);
        when(queryResult.sounds()).thenReturn(sounds);
        when(queryResult.ownerID()).thenReturn(ownerId);

        // when
        Map<UUID, UUID> result = sut.extractFileOwnerMap(queryResult);

        // then
        assertThat(result).containsOnlyKeys(audioId1, audioId2);
        result.values().forEach(value -> assertThat(value).isEqualTo(ownerId));
    }

}