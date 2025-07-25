package com.hoo.universe.application;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.api.in.dto.OpenUniverseResult;
import com.hoo.universe.api.out.FileUrlResolveInCase;
import com.hoo.universe.api.out.LoadUniversePort;
import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Space;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.vo.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.*;

import static com.hoo.universe.test.domain.PieceTestData.defaultPiece;
import static com.hoo.universe.test.domain.ShapeTestData.createNonOverlappingShapes;
import static com.hoo.universe.test.domain.SpaceTestData.defaultSpace;
import static com.hoo.universe.test.domain.UniverseTestData.*;
import static com.hoo.universe.test.dto.PageableTestData.defaultPageable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class OpenUniverseServiceTest {

    LoadUniversePort loadUniversePort = mock();
    FileUrlResolveInCase fileUrlResolveInCase = mock();
    FileOwnerMapExtractor fileOwnerMapExtractor = mock();
    ApplicationMapper applicationMapper = mock();

    OpenUniverseService sut = new OpenUniverseService(loadUniversePort, fileUrlResolveInCase, fileOwnerMapExtractor, applicationMapper);

    @Test
    @DisplayName("유니버스 열기 서비스")
    void openUniverseService() {
        // given
        UUID universeId = UuidCreator.getTimeOrderedEpoch();
        Universe universe = mock();
        Map<UUID, UUID> fileOwnerMap = mock();
        Map<UUID, URI> fileUrlMap = mock();

        when(loadUniversePort.loadUniverseExceptSounds(universeId)).thenReturn(universe);
        when(fileOwnerMapExtractor.extractFileOwnerMap(universe)).thenReturn(fileOwnerMap);
        when(fileUrlResolveInCase.resolveBatch(fileOwnerMap)).thenReturn(fileUrlMap);
        when(applicationMapper.mapToOpenUniverseResult(universe, fileUrlMap)).thenReturn(mock(OpenUniverseResult.class));

        // when
        sut.openUniverseWithComponents(universeId);

        // then
        verify(loadUniversePort, times(1)).loadUniverseExceptSounds(universeId);
        verify(fileOwnerMapExtractor, times(1)).extractFileOwnerMap(universe);
        verify(fileUrlResolveInCase, times(1)).resolveBatch(fileOwnerMap);
        verify(applicationMapper, times(1)).mapToOpenUniverseResult(universe, fileUrlMap);
    }

}