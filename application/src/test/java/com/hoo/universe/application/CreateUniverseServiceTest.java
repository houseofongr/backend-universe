package com.hoo.universe.application;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.IssueIDPort;
import com.hoo.common.internal.api.dto.UploadFileRequest;
import com.hoo.universe.api.dto.command.CreateUniverseCommand;
import com.hoo.common.internal.api.FileUploadAPI;
import com.hoo.universe.api.out.persistence.HandleUniverseEventPort;
import com.hoo.universe.api.out.persistence.QueryCategoryPort;
import com.hoo.universe.api.out.internal.FindAuthorAPI;
import com.hoo.universe.application.exception.ApplicationErrorCode;
import com.hoo.universe.domain.vo.Author;
import com.hoo.universe.domain.vo.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.*;

import static com.hoo.universe.test.dto.UploadFileTestData.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateUniverseServiceTest {

    IssueIDPort issueIDPort = mock();
    FindAuthorAPI findAuthorAPI = mock();
    QueryCategoryPort queryCategoryPort = mock();
    HandleUniverseEventPort handleUniverseEventPort = mock();
    FileUploadAPI fileUploadAPI = mock();

    CreateUniverseService sut = new CreateUniverseService(issueIDPort, findAuthorAPI, queryCategoryPort, handleUniverseEventPort, fileUploadAPI);

    InputStream mockInputStream = mock();

    @Test
    @DisplayName("썸네일, 썸뮤직, 내부이미지 용량 초과")
    void testFileSizeExceeded() {
        // given
        CreateUniverseCommand.Metadata metadata = new CreateUniverseCommand.Metadata("우주", "유니버스는 우주입니다.", UuidCreator.getTimeOrderedEpoch(), UuidCreator.getTimeOrderedEpoch(), "PUBLIC", List.of());

        UploadFileRequest thumbmusic = new UploadFileRequest("thumbmusic.mp3", 2 * 1024 * 1024 + 1L, mockInputStream);
        UploadFileRequest thumbnail = new UploadFileRequest("thumbnail.png", 2 * 1024 * 1024 + 1L, mockInputStream);
        UploadFileRequest background = new UploadFileRequest("background.png", 100 * 1024 * 1024 + 1L, mockInputStream);

        CreateUniverseCommand command = new CreateUniverseCommand(metadata, thumbmusic, thumbnail, background);

        // then
        assertThatThrownBy(() -> sut.createNewUniverse(command)).hasMessage(ApplicationErrorCode.EXCEEDED_FILE_SIZE.getMessage());
    }

    @Test
    @DisplayName("유니버스 생성 서비스")
    void testUniverseCreateService() {
        // given
        CreateUniverseCommand.Metadata metadata = new CreateUniverseCommand.Metadata("우주", "유니버스는 우주입니다.", UuidCreator.getTimeOrderedEpoch(), UuidCreator.getTimeOrderedEpoch(), "PUBLIC", List.of());
        UploadFileRequest audio = defaultAudioFileRequest();
        UploadFileRequest image = defaultImageFileRequest();
        CreateUniverseCommand command = new CreateUniverseCommand(metadata, audio, image, image);
        Author newAuthor = new Author(UuidCreator.getTimeOrderedEpoch(),"leaffael");

        // when
        when(findAuthorAPI.findAuthor(command.metadata().authorID())).thenReturn(newAuthor);
        when(queryCategoryPort.findUniverseCategory(any())).thenReturn(new Category(UuidCreator.getTimeOrderedEpoch(), "category", "카테고리"));
        when(fileUploadAPI.uploadFile(audio)).thenReturn(defaultAudioFileResponse());
        when(fileUploadAPI.uploadFile(image)).thenReturn(defaultImageFileResponse());

        sut.createNewUniverse(command);

        // then
        verify(issueIDPort, times(1)).issueNewID();
        verify(handleUniverseEventPort, times(1)).handleCreateUniverseEvent(any());
    }

}