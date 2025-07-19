package com.hoo.universe.application.piece;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.IssueIDPort;
import com.hoo.universe.api.in.web.dto.command.CreatePieceWithTwoPointCommand;
import com.hoo.universe.api.out.persistence.HandlePieceEventPort;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.application.CreatePieceService;
import com.hoo.universe.application.exception.DomainErrorCode;
import com.hoo.universe.application.exception.UniverseDomainException;
import com.hoo.universe.domain.Universe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverseOnly;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class CreatePieceServiceTest {

    IssueIDPort issueIDPort = mock();
    LoadUniversePort loadUniversePort = mock();
    HandlePieceEventPort handlePieceEventPort = mock();

    CreatePieceService sut = new CreatePieceService(issueIDPort, loadUniversePort, handlePieceEventPort);

    @Test
    @DisplayName("피스 생성 서비스")
    void createPieceService() {
        // given
        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        CreatePieceWithTwoPointCommand command = new CreatePieceWithTwoPointCommand(new CreatePieceWithTwoPointCommand.Metadata(null, "조각", "피스는 조각입니다.", 0.1, 0.1, 0.2, 0.2, false), null);
        Universe universe = defaultUniverseOnly().build();

        // when
        when(loadUniversePort.loadUniverseExceptSounds(universeID)).thenReturn(universe);
        sut.createNewPieceWithTwoPoint(universeID, command);

        // then
        verify(issueIDPort, times(1)).issueNewID();
        verify(loadUniversePort, times(1)).loadUniverseExceptSounds(any());
        verify(handlePieceEventPort, times(1)).handlePieceCreateEvent(any());

        // 같은 위치에 두번 생성시 실패
        assertThatThrownBy(() -> sut.createNewPieceWithTwoPoint(universeID, command))
                .isInstanceOf(UniverseDomainException.class)
                .hasMessageContaining(DomainErrorCode.OVERLAPPED.getMessage());
    }

}