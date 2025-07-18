package com.hoo.universe.test.dto;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.internal.api.dto.UploadFileRequest;
import com.hoo.common.internal.api.dto.UploadFileResponse;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static com.hoo.common.enums.Authority.PUBLIC_FILE_ACCESS;
import static com.hoo.common.enums.FileType.IMAGE;
import static java.nio.charset.StandardCharsets.UTF_8;

public class UploadFileTestData {

    public static UploadFileRequest defaultImageFileRequest() {
        InputStream inputStream = new ByteArrayInputStream("image file".getBytes(UTF_8));
        return new UploadFileRequest("image.png", 1000L, inputStream);
    }

    public static UploadFileRequest defaultAudioFileRequest() {
        InputStream inputStream = new ByteArrayInputStream("sound file".getBytes(UTF_8));
        return new UploadFileRequest("sound.mp3", 1000L, inputStream);
    }

    public static UploadFileResponse defaultImageFileResponse() {
        return new UploadFileResponse(UuidCreator.getTimeOrderedEpoch(), null, 1000L, "uploaded_image.png", "test1234.png", IMAGE, PUBLIC_FILE_ACCESS);
    }

    public static UploadFileResponse defaultAudioFileResponse() {
        return new UploadFileResponse(UuidCreator.getTimeOrderedEpoch(), null, 1000L, "uploaded_audio.mp3", "test4567.mp3", IMAGE, PUBLIC_FILE_ACCESS);
    }
}
