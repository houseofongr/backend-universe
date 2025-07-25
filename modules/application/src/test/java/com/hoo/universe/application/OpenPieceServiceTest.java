package com.hoo.universe.application;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.common.web.dto.PageRequest;
import com.hoo.universe.api.in.dto.OpenPieceCommand;
import com.hoo.universe.api.in.dto.SearchUniverseCommand;
import com.hoo.universe.api.out.FileUrlResolveInCase;
import com.hoo.universe.api.out.dto.OpenPieceQueryResult;
import com.hoo.universe.api.in.dto.OpenPieceResult;
import com.hoo.universe.api.out.QueryUniversePort;
import com.hoo.universe.domain.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.*;

import static com.hoo.universe.test.domain.PieceTestData.defaultPiece;
import static com.hoo.universe.test.dto.PageableTestData.defaultPageable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class OpenPieceServiceTest {

    QueryUniversePort queryUniversePort = mock();
    FileUrlResolveInCase fileUrlResolveInCase = mock();
    FileOwnerMapExtractor fileOwnerMapExtractor = mock();
    ApplicationMapper applicationMapper = mock();

    OpenPieceService sut = new OpenPieceService(queryUniversePort, fileUrlResolveInCase, fileOwnerMapExtractor, applicationMapper);

    @Test
    @DisplayName("피스 열기 서비스")
    void openPieceService() {
        // given
        UUID pieceId = UuidCreator.getTimeOrderedEpoch();
        PageRequest pageRequest = mock(PageRequest.class);
        OpenPieceQueryResult queryResult = mock(OpenPieceQueryResult.class);
        Map<UUID, UUID> fileOwnerMap = mock(Map.class);
        Map<UUID, URI> fileUrlMap = mock(Map.class);

        when(queryUniversePort.searchPiece(pieceId, pageRequest)).thenReturn(queryResult);
        when(fileOwnerMapExtractor.extractFileOwnerMap(queryResult)).thenReturn(fileOwnerMap);
        when(fileUrlResolveInCase.resolveBatch(fileOwnerMap)).thenReturn(fileUrlMap);
        when(applicationMapper.mapToOpenPieceResult(queryResult, fileUrlMap)).thenReturn(mock(OpenPieceResult.class));

        // when
        sut.openPieceWithSounds(pieceId, pageRequest);

        // then
        verify(queryUniversePort, times(1)).searchPiece(pieceId, pageRequest);
        verify(fileOwnerMapExtractor, times(1)).extractFileOwnerMap(queryResult);
        verify(fileUrlResolveInCase, times(1)).resolveBatch(fileOwnerMap);
        verify(applicationMapper, times(1)).mapToOpenPieceResult(queryResult, fileUrlMap);
    }

}