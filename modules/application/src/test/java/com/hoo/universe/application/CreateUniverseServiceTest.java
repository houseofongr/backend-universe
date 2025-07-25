package com.hoo.universe.application;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.IssueIDPort;
import com.hoo.common.internal.api.file.UploadFileAPI;
import com.hoo.common.internal.api.file.dto.UploadFileCommand;
import com.hoo.common.internal.api.user.GetUserInfoAPI;
import com.hoo.common.internal.api.user.dto.UserInfo;
import com.hoo.universe.api.in.dto.CreateUniverseCommand;
import com.hoo.universe.api.out.QueryCategoryPort;
import com.hoo.universe.api.out.SaveEntityPort;
import com.hoo.universe.application.exception.ApplicationErrorCode;
import com.hoo.universe.domain.vo.Category;
import com.hoo.universe.domain.vo.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static com.hoo.universe.test.dto.FileTestData.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class CreateUniverseServiceTest {

    IssueIDPort issueIDPort = mock();
    GetUserInfoAPI getOwnerAPI = mock();
    QueryCategoryPort queryCategoryPort = mock();
    SaveEntityPort saveEntityPort = mock();
    UploadFileAPI uploadFileAPI = mock();

    CreateUniverseService sut = new CreateUniverseService(issueIDPort, getOwnerAPI, queryCategoryPort, saveEntityPort, uploadFileAPI);

    @Test
    @DisplayName("썸네일, 썸뮤직, 내부이미지 용량 초과")
    void testFileSizeExceeded() {
        // given
        CreateUniverseCommand.Metadata metadata = new CreateUniverseCommand.Metadata("우주", "유니버스는 우주입니다.", UuidCreator.getTimeOrderedEpoch(), UuidCreator.getTimeOrderedEpoch(), "PUBLIC", List.of());

        UploadFileCommand.FileSource thumbmusic = new UploadFileCommand.FileSource(null, "audio/mp3", "thumbmusic.mp3", 2 * 1024 * 1024 + 1L);
        UploadFileCommand.FileSource thumbnail = new UploadFileCommand.FileSource(null, "image/png", "thumbnail.png", 2 * 1024 * 1024 + 1L);
        UploadFileCommand.FileSource background = new UploadFileCommand.FileSource(null, "image/png", "background.png", 100 * 1024 * 1024 + 1L);

        Owner newOwner = new Owner(UuidCreator.getTimeOrderedEpoch(), "leaffael");

        CreateUniverseCommand command = new CreateUniverseCommand(metadata, thumbmusic, thumbnail, background);

        // then
        assertThatThrownBy(() -> sut.createNewUniverse(command)).hasMessage(ApplicationErrorCode.EXCEEDED_FILE_SIZE.getMessage());
    }

    @Test
    @DisplayName("유니버스 생성 서비스")
    void testUniverseCreateService() {
        // given
        CreateUniverseCommand.Metadata metadata = new CreateUniverseCommand.Metadata("우주", "유니버스는 우주입니다.", UuidCreator.getTimeOrderedEpoch(), UuidCreator.getTimeOrderedEpoch(), "PUBLIC", List.of());
        UploadFileCommand.FileSource audio = defaultAudioFileSource();
        UploadFileCommand.FileSource image = defaultImageFileSource();
        CreateUniverseCommand command = new CreateUniverseCommand(metadata, audio, image, image);
        Owner newOwner = new Owner(UuidCreator.getTimeOrderedEpoch(), "leaffael");

        // when
        when(getOwnerAPI.getUserInfo(command.metadata().ownerID())).thenReturn(new UserInfo(command.metadata().ownerID(), true, true, "test@example.com", "leaf", "BUSINESS", "ACTIVATE", ZonedDateTime.now().toEpochSecond()));
        when(queryCategoryPort.findUniverseCategory(any())).thenReturn(new Category(UuidCreator.getTimeOrderedEpoch(), "category", "카테고리"));
        when(uploadFileAPI.uploadFile(any())).thenReturn(defaultFileResponse());
        when(uploadFileAPI.uploadFile(any())).thenReturn(defaultFileResponse());

        sut.createNewUniverse(command);

        // then
        verify(issueIDPort, times(1)).issueNewID();
        verify(saveEntityPort, times(1)).saveUniverse(any());
    }

}