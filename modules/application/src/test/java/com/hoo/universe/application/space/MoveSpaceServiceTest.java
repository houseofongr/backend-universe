package com.hoo.universe.application.space;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.api.in.web.dto.command.MoveSpaceWithTwoPointCommand;
import com.hoo.universe.api.out.persistence.HandleSpaceEventPort;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.application.exception.DomainErrorCode;
import com.hoo.universe.domain.Universe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverse;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class MoveSpaceServiceTest {

    LoadUniversePort loadUniversePort = mock();
    HandleSpaceEventPort handleSpaceEventPort = mock();

    MoveSpaceService sut = new MoveSpaceService(loadUniversePort, handleSpaceEventPort);

    @Test
    @DisplayName("스페이스 이동 불가 시 오류처리")
    void handExceptionWhenSpaceCannotMove() {
        // given
        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        MoveSpaceWithTwoPointCommand command = new MoveSpaceWithTwoPointCommand(0.0, 0.0, 0.5, 0.5);
        Universe universe = defaultUniverse();
        UUID spaceID = universe.getSpaces().getFirst().getId().uuid();

        // when
        when(loadUniversePort.loadUniverseExceptSounds(universeID)).thenReturn(universe);

        // then
        assertThatThrownBy(() -> sut.moveSpaceWithTwoPoint(universeID, spaceID, command)).hasMessage(DomainErrorCode.OVERLAPPED.getMessage());
    }

    @Test
    @DisplayName("두 점으로 스페이스 이동 서비스")
    void moveSpaceWithTwoPointService() {
        // given
        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        MoveSpaceWithTwoPointCommand command = new MoveSpaceWithTwoPointCommand(0.7, 0.7, 0.8, 0.8);
        Universe universe = defaultUniverse();
        UUID spaceID = universe.getSpaces().getFirst().getId().uuid();

        // when
        when(loadUniversePort.loadUniverseExceptSounds(universeID)).thenReturn(universe);
        sut.moveSpaceWithTwoPoint(universeID, spaceID, command);

        // then
        verify(handleSpaceEventPort, times(1)).handleSpaceMoveEvent(any());
    }

}