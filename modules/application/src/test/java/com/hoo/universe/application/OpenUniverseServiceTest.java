package com.hoo.universe.application;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.api.in.web.dto.result.OpenUniverseResult;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Space;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.Outline;
import com.hoo.universe.domain.vo.PieceMetadata;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.*;

import static com.hoo.universe.test.domain.PieceTestData.defaultPiece;
import static com.hoo.universe.test.domain.ShapeTestData.createNonOverlappingShapes;
import static com.hoo.universe.test.domain.SpaceTestData.defaultSpace;
import static com.hoo.universe.test.domain.UniverseTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class OpenUniverseServiceTest {

    LoadUniversePort loadUniversePort = mock();
    FileUrlResolver fileUrlResolver = mock();


    OpenUniverseService sut = new OpenUniverseService(loadUniversePort, fileUrlResolver);

    @Test
    @DisplayName("유니버스 열기 서비스")
    void testOpenUniverse() {
        // given
        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        Universe universe = defaultUniverseOnly().build();

        List<Outline> outlines = createNonOverlappingShapes(2);
        Space 스페이스1 = defaultSpace()
                .withUniverse(universe)
                .withSpaceID(new Space.SpaceID(UuidCreator.getTimeOrderedEpoch()))
                .withOutline(outlines.get(0))
                .withCommonMetadata(CommonMetadata.create("스페이스1", "유니버스의 첫번째 스페이스")).build();

        Piece 피스1 = defaultPiece()
                .withUniverse(universe)
                .withPieceID(new Piece.PieceID(UuidCreator.getTimeOrderedEpoch()))
                .withParentSpaceID(스페이스1.getId())
                .withOutline(outlines.get(1))
                .withPieceMetadata(PieceMetadata.create(null, false))
                .withCommonMetadata(CommonMetadata.create("피스1", "유니버스의 첫번째 피스")).build();

        Map<UUID, URI> uriMap = new HashMap<>();
        uriMap.put(universe.getUniverseMetadata().getThumbmusicID(), URI.create("1"));
        uriMap.put(universe.getUniverseMetadata().getThumbnailID(), URI.create("2"));
        uriMap.put(universe.getUniverseMetadata().getBackgroundID(), URI.create("3"));
        uriMap.put(스페이스1.getSpaceMetadata().getBackgroundID(), URI.create("4"));

        // when
        when(loadUniversePort.loadUniverseExceptSounds(universeID)).thenReturn(universe);
        when(fileUrlResolver.resolveBatch((Collection<UUID>) any())).thenReturn(uriMap);
        OpenUniverseResult result = sut.openUniverseWithComponents(universeID);

        // then
        verify(loadUniversePort, times(1)).loadUniverseExceptSounds(universeID);
        verify(fileUrlResolver, times(1)).resolveBatch((Collection<UUID>) any());

        assertThat(result.thumbmusicFileUrl().toString()).isEqualTo("1");
        assertThat(result.thumbnailFileUrl().toString()).isEqualTo("2");
        assertThat(result.backgroundFileUrl().toString()).isEqualTo("3");
        assertThat(result.spaces().getFirst().backgroundFileUrl().toString()).isEqualTo("4");
        assertThat(result.spaces().getFirst().pieces().getFirst().imageFileUrl()).isNull();
    }

}