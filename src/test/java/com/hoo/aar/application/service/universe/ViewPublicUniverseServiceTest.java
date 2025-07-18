package com.hoo.aar.application.service.universe;

import com.hoo.aar.application.port.in.universe.ViewPublicUniverseResult;
import com.hoo.aar.application.port.out.persistence.universe.CheckIsLikedUniversePort;
import com.hoo.aar.application.port.out.persistence.universe.ViewPublicUniversePort;
import com.hoo.aar.application.service.AarErrorCode;
import com.hoo.admin.domain.universe.MockTreeInfo;
import com.hoo.admin.domain.universe.TraversalComponents;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ViewPublicUniverseServiceTest {

    ViewPublicUniversePort viewPublicUniversePort = mock();
    CheckIsLikedUniversePort checkIsLikedUniversePort = mock();

    ViewPublicUniverseService sut = new ViewPublicUniverseService(viewPublicUniversePort, checkIsLikedUniversePort);

    @Test
    @DisplayName("본인 소유가 아닌 유니버스에 접근")
    void testAccessNotOwnedUniverse() {
        // given
        TraversalComponents privateUniverse = MockTreeInfo.getPrivateTraversalComponent();

        // bad base
        when(viewPublicUniversePort.viewPublicUniverse(privateUniverse.getUniverse().getId())).thenReturn(privateUniverse);
        assertThatThrownBy(() -> sut.read(privateUniverse.getUniverse().getId(), 123L))
                .hasMessage(AarErrorCode.NOT_OWNED_PRIVATE_UNIVERSE_ACCESS.getMessage());

        // happy case
        sut.read(privateUniverse.getUniverse().getId(), 1L);
    }

    @Test
    @DisplayName("유니버스 보기 서비스")
    void testViewUniverseService() {
        // given
        Long universeId = 1L;
        Long userId = 1L;
        TraversalComponents components = MockTreeInfo.getTraversalComponent();

        // when
        when(checkIsLikedUniversePort.checkIsLiked(any(), any())).thenReturn(true);
        when(viewPublicUniversePort.viewPublicUniverse(universeId)).thenReturn(components);
        ViewPublicUniverseResult result = sut.read(universeId, userId);

        // then
        assertThat(result.universeId()).isEqualTo(1L);
        assertThat(result.thumbmusicId()).isEqualTo(1L);
        assertThat(result.thumbnailId()).isEqualTo(2L);
        assertThat(result.innerImageId()).isEqualTo(3L);
        assertThat(result.ownerId()).isEqualTo(1L);
        assertThat(result.createdTime()).isBetween(ZonedDateTime.now().minus(Duration.of(1, ChronoUnit.SECONDS)).toEpochSecond(), ZonedDateTime.now().toEpochSecond());
        assertThat(result.updatedTime()).isBetween(ZonedDateTime.now().minus(Duration.of(1, ChronoUnit.SECONDS)).toEpochSecond(), ZonedDateTime.now().toEpochSecond());
        assertThat(result.view()).isEqualTo(5L);
        assertThat(result.like()).isEqualTo(2);
        assertThat(result.title()).isEqualTo("new universe");
        assertThat(result.description()).isEqualTo("새 유니버스");
        assertThat(result.owner()).isEqualTo("leaf");
        assertThat(result.hashtags()).hasSize(3);
        assertThat(result.isMine()).isTrue();
        assertThat(result.isLiked()).isTrue();
        assertThat(result.spaces()).hasSize(2)
                .anySatisfy(spaceTreeInfo -> {
                    assertThat(spaceTreeInfo.spaceId()).isEqualTo(1L);
                    assertThat(spaceTreeInfo.spaces()).hasSize(1)
                            .anySatisfy(spaceTreeInfo1 -> {
                                assertThat(spaceTreeInfo1.spaceId()).isEqualTo(3L);
                                assertThat(spaceTreeInfo1.pieces().getFirst().pieceId()).isEqualTo(3L);
                            });
                    assertThat(spaceTreeInfo.pieces()).hasSize(1);
                })
                .anySatisfy(spaceTreeInfo -> {
                    assertThat(spaceTreeInfo.spaceId()).isEqualTo(2L);
                    assertThat(spaceTreeInfo.spaces()).hasSize(2)
                            .anySatisfy(spaceTreeInfo1 -> {
                                assertThat(spaceTreeInfo1.spaceId()).isEqualTo(4L);
                                assertThat(spaceTreeInfo1.pieces()).hasSize(2);
                            })
                            .anySatisfy(spaceTreeInfo1 -> {
                                assertThat(spaceTreeInfo1.spaceId()).isEqualTo(5L);
                                assertThat(spaceTreeInfo1.pieces()).hasSize(2);
                            });
                });
        assertThat(result.pieces()).hasSize(1);
    }

}