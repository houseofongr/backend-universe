package com.hoo.universe.application.piece;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.api.dto.command.piece.MovePieceWithTwoPointCommand;
import com.hoo.universe.api.out.persistence.HandlePieceEventPort;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.domain.Universe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverse;
import static org.mockito.Mockito.*;

class MovePieceServiceTest {

    LoadUniversePort loadUniversePort = mock();
    HandlePieceEventPort handlePieceEventPort = mock();

    MovePieceService sut = new MovePieceService(loadUniversePort, handlePieceEventPort);

    @Test
    @DisplayName("두 점으로 피스 이동 서비스")
    void movePieceWithTwoPointService() {
        // given
        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        MovePieceWithTwoPointCommand command = new MovePieceWithTwoPointCommand(0.7, 0.7, 0.8, 0.8);
        Universe universe = defaultUniverse();
        UUID pieceID = universe.getPieces().getFirst().getId().uuid();

        // when
        when(loadUniversePort.loadUniverseExceptSounds(universeID)).thenReturn(universe);
        sut.movePieceWithTwoPoint(universeID, pieceID, command);

        // then
        verify(handlePieceEventPort, times(1)).handlePieceMoveEvent(any());
    }
}