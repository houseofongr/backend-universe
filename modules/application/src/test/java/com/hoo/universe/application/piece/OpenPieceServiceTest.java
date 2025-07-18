package com.hoo.universe.application.piece;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.common.web.dto.PageRequest;
import com.hoo.universe.api.dto.query.OpenPieceQueryResult;
import com.hoo.universe.api.dto.result.piece.OpenPieceResult;
import com.hoo.universe.api.out.persistence.QueryUniversePort;
import com.hoo.universe.application.FileUrlResolver;
import com.hoo.universe.domain.Piece;
import org.assertj.core.api.Assertions;
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
    FileUrlResolver fileUrlResolver = mock();

    OpenPieceService sut = new OpenPieceService(queryUniversePort, fileUrlResolver);

    @Test
    @DisplayName("피스 열기 서비스")
    void openPieceService() {
        // given
        UUID pieceID = UuidCreator.getTimeOrderedEpoch();
        PageRequest pageRequest = defaultPageable();

        Piece piece = defaultPiece().build();
        PageQueryResult<OpenPieceQueryResult.SoundInfo> soundQueryResult = PageQueryResult.from(pageRequest, 1L,
                List.of(new OpenPieceQueryResult.SoundInfo(
                        UuidCreator.getTimeOrderedEpoch(),
                        UuidCreator.getTimeOrderedEpoch(),
                        "소리", "소리소리", false, ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().toEpochSecond()))
        );

        OpenPieceQueryResult queryResult = OpenPieceQueryResult.from(piece, soundQueryResult);

        Map<UUID, URI> uriMap = new HashMap<>();
        uriMap.put(soundQueryResult.content().getLast().audioFileID(), URI.create("1"));

        // when
        when(queryUniversePort.searchPiece(pieceID, pageRequest)).thenReturn(queryResult);
        when(fileUrlResolver.resolveBatch((Collection<UUID>) any())).thenReturn(uriMap);
        OpenPieceResult result = sut.openPieceWithSounds(pieceID, pageRequest);

        // then
        assertThat(result.sounds().content().getFirst().audioFileUrl().toString()).isEqualTo("1");
    }

}