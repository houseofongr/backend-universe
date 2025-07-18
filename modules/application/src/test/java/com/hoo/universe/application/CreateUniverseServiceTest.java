package com.hoo.universe.application;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.IssueIDPort;
import com.hoo.common.internal.api.dto.FileCommand;
import com.hoo.common.internal.api.dto.UploadFileCommand;
import com.hoo.universe.api.dto.command.CreateUniverseCommand;
import com.hoo.common.internal.api.UploadFileAPI;
import com.hoo.universe.api.out.persistence.HandleUniverseEventPort;
import com.hoo.universe.api.out.persistence.QueryCategoryPort;
import com.hoo.universe.api.out.internal.FindOwnerAPI;
import com.hoo.universe.application.exception.ApplicationErrorCode;
import com.hoo.universe.domain.vo.Owner;
import com.hoo.universe.domain.vo.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.*;

import static com.hoo.universe.test.dto.FileTestData.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateUniverseServiceTest {

    IssueIDPort issueIDPort = mock();
    FindOwnerAPI findOwnerAPI = mock();
    QueryCategoryPort queryCategoryPort = mock();
    HandleUniverseEventPort handleUniverseEventPort = mock();
    UploadFileAPI uploadFileAPI = mock();

    CreateUniverseService sut = new CreateUniverseService(issueIDPort, findOwnerAPI, queryCategoryPort, handleUniverseEventPort, uploadFileAPI);

    @Test
    @DisplayName("썸네일, 썸뮤직, 내부이미지 용량 초과")
    void testFileSizeExceeded() {
        // given
        CreateUniverseCommand.Metadata metadata = new CreateUniverseCommand.Metadata("우주", "유니버스는 우주입니다.", UuidCreator.getTimeOrderedEpoch(), UuidCreator.getTimeOrderedEpoch(), "PUBLIC", List.of());

        FileCommand thumbmusic = new FileCommand(null, 2 * 1024 * 1024 + 1L, "thumbmusic.mp3", "audio/mp3");
        FileCommand thumbnail = new FileCommand(null, 2 * 1024 * 1024 + 1L,"thumbnail.png", "image/png");
        FileCommand background = new FileCommand(null, 100 * 1024 * 1024 + 1L,"background.png", "image/png");

        Owner newOwner = new Owner(UuidCreator.getTimeOrderedEpoch(),"leaffael");

        CreateUniverseCommand command = new CreateUniverseCommand(metadata, thumbmusic, thumbnail, background);

        // then
        assertThatThrownBy(() -> sut.createNewUniverse(command)).hasMessage(ApplicationErrorCode.EXCEEDED_FILE_SIZE.getMessage());
    }

    @Test
    @DisplayName("유니버스 생성 서비스")
    void testUniverseCreateService() {
        // given
        CreateUniverseCommand.Metadata metadata = new CreateUniverseCommand.Metadata("우주", "유니버스는 우주입니다.", UuidCreator.getTimeOrderedEpoch(), UuidCreator.getTimeOrderedEpoch(), "PUBLIC", List.of());
        FileCommand audio = defaultAudioFileCommand();
        FileCommand image = defaultImageFileCommand();
        CreateUniverseCommand command = new CreateUniverseCommand(metadata, audio, image, image);
        Owner newOwner = new Owner(UuidCreator.getTimeOrderedEpoch(),"leaffael");

        // when
        when(findOwnerAPI.findOwner(command.metadata().ownerID())).thenReturn(newOwner);
        when(queryCategoryPort.findUniverseCategory(any())).thenReturn(new Category(UuidCreator.getTimeOrderedEpoch(), "category", "카테고리"));
        when(uploadFileAPI.uploadFile(any())).thenReturn(defaultFileResponse());
        when(uploadFileAPI.uploadFile(any())).thenReturn(defaultFileResponse());

        sut.createNewUniverse(command);

        // then
        verify(issueIDPort, times(1)).issueNewID();
        verify(handleUniverseEventPort, times(1)).handleCreateUniverseEvent(any());
    }

}