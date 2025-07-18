package com.hoo.universe.application.piece;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.web.dto.PageRequest;
import com.hoo.universe.api.out.persistence.QueryUniversePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.hoo.universe.test.dto.PageableTestData.defaultPageable;
import static org.mockito.Mockito.*;

class OpenPieceServiceTest {

    QueryUniversePort queryUniversePort = mock();

    OpenPieceService sut = new OpenPieceService(queryUniversePort);

    @Test
    @DisplayName("피스 열기 서비스")
    void openPieceService() {
        // given
        UUID pieceID = UuidCreator.getTimeOrderedEpoch();
        PageRequest pageRequest = defaultPageable();

        // when
        sut.openPieceWithSounds(pieceID, pageRequest);

        // then
        verify(queryUniversePort, times(1)).searchPiece(pieceID, pageRequest);
    }

}