package com.hoo.admin.application.service.house;

import com.hoo.admin.application.port.in.house.CreateHouseMetadata;
import com.hoo.admin.application.port.in.house.CreateHouseResult;
import com.hoo.admin.application.port.out.house.CreateHousePort;
import com.hoo.admin.application.port.out.house.CreateRoomPort;
import com.hoo.admin.application.port.out.house.SaveHousePort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.common.domain.Authority;
import com.hoo.file.application.port.in.UploadFileResult;
import com.hoo.file.application.port.in.UploadPrivateImageUseCase;
import com.hoo.file.domain.FileSize;
import com.hoo.file.domain.exception.FileSizeLimitExceedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hoo.admin.application.port.in.house.CreateHouseMetadataTest.getCreateHouseMetadata;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateHouseServiceTest {

    CreateHouseService sut;

    SaveHousePort saveHousePort;
    UploadPrivateImageUseCase uploadPrivateImageUseCase;
    CreateHousePort createHousePort;
    CreateRoomPort createRoomPort;

    @BeforeEach
    void init() {
        saveHousePort = mock();
        uploadPrivateImageUseCase = mock();
        createHousePort = mock();
        createRoomPort = mock();
        sut = new CreateHouseService(saveHousePort, uploadPrivateImageUseCase, createHousePort, createRoomPort);
    }

    @Test
    @DisplayName("하우스 생성 시 보더 이미지와 기본 이미지 중복 테스트")
    void testCreateHouseDuplicateImages() throws FileSizeLimitExceedException {
        // given
        CreateHouseMetadata metadata = getCreateHouseMetadata();

        Map<String, MultipartFile> map = new HashMap<>();

        map.put("house", new MockMultipartFile("house", "house.png", "image/png", "house file".getBytes()));
        map.put("border", new MockMultipartFile("border", "house.png", "image/png", "border file".getBytes()));

        // when
        when(uploadPrivateImageUseCase.privateUpload(any())).thenReturn(new UploadFileResult(List.of(new UploadFileResult.FileInfo(1L, null, "newfile.png", "newfile1241325.png", new FileSize(1234L, 10000L).getUnitSize(), Authority.ALL_PRIVATE_IMAGE_ACCESS))));

        // then
        assertThatThrownBy(() -> sut.create(metadata, map)).hasMessage(AdminErrorCode.DUPLICATE_HOUSE_PROFILE_IMAGE_AND_BORDER_IMAGE.getMessage());
    }

    @Test
    @DisplayName("하우스 생성 테스트")
    void testCreateHouse() throws FileSizeLimitExceedException {
        // given
        CreateHouseMetadata metadata = getCreateHouseMetadata();

        Map<String, MultipartFile> map = new HashMap<>();

        map.put("house", new MockMultipartFile("house", "house.png", "image/png", "house file".getBytes()));
        map.put("border", new MockMultipartFile("border", "border.png", "image/png", "border file".getBytes()));
        map.put("room1", new MockMultipartFile("room1", "livingRoom.png", "image/png", "livingRoom file".getBytes()));
        map.put("room2", new MockMultipartFile("room2", "kitchen.png", "image/png", "kitchen file".getBytes()));

        // when
        when(uploadPrivateImageUseCase.privateUpload(any())).thenReturn(new UploadFileResult(List.of(new UploadFileResult.FileInfo(1L, null, "newfile.png", "newfile1241325.png", new FileSize(1234L, 10000L).getUnitSize(), Authority.ALL_PRIVATE_IMAGE_ACCESS))));
        CreateHouseResult result = sut.create(metadata, map);

        // then
        verify(uploadPrivateImageUseCase, times(1)).privateUpload(any());
        verify(saveHousePort, times(1)).save(any());

        assertThat(result.houseId()).isNotNull();
    }

}