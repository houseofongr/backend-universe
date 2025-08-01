package com.hoo.universe.application;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.IssueIDPort;
import com.hoo.common.internal.api.file.UploadFileAPI;
import com.hoo.common.internal.api.file.dto.UploadFileCommand;
import com.hoo.universe.api.in.dto.CreateSpaceWithTwoPointCommand;
import com.hoo.universe.api.out.LoadUniversePort;
import com.hoo.universe.api.out.SaveEntityPort;
import com.hoo.universe.application.exception.DomainErrorCode;
import com.hoo.universe.application.exception.UniverseDomainException;
import com.hoo.universe.domain.Universe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverseOnly;
import static com.hoo.universe.test.dto.FileTestData.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class CreateSpaceServiceTest {

    IssueIDPort issueIDPort = mock();
    LoadUniversePort loadUniversePort = mock();
    SaveEntityPort saveEntityPort = mock();
    UploadFileAPI uploadFileAPI = mock();

    CreateSpaceService sut = new CreateSpaceService(issueIDPort, loadUniversePort, saveEntityPort, uploadFileAPI);

    @Test
    @DisplayName("스페이스 생성 서비스")
    void createSpaceService() {
        // given
        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        UploadFileCommand.FileSource background = defaultImageFileSource();
        CreateSpaceWithTwoPointCommand command = new CreateSpaceWithTwoPointCommand(new CreateSpaceWithTwoPointCommand.Metadata(null, "공간", "스페이스는 공간입니다.", 0.1, 0.1, 0.2, 0.2, false), background);
        Universe universe = defaultUniverseOnly().build();

        // when
        when(loadUniversePort.loadUniverseExceptSounds(universeID)).thenReturn(universe);
        when(uploadFileAPI.uploadFile(any())).thenReturn(defaultFileResponse());
        sut.createSpaceWithTwoPoint(universeID, null, command);

        // then
        verify(issueIDPort, times(1)).issueNewID();
        verify(loadUniversePort, times(1)).loadUniverseExceptSounds(any());
        verify(uploadFileAPI, times(1)).uploadFile(any());
        verify(saveEntityPort, times(1)).saveSpace(any());

        // 같은 위치에 두번 생성 시 실패
        assertThatThrownBy(() -> sut.createSpaceWithTwoPoint(universeID, null, command))
                .isInstanceOf(UniverseDomainException.class)
                .hasMessageContaining(DomainErrorCode.OVERLAPPED.getMessage());
    }
}