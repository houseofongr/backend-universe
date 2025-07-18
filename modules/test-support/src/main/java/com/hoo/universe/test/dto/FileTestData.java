package com.hoo.universe.test.dto;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.enums.AccessLevel;
import com.hoo.common.internal.api.dto.FileCommand;
import com.hoo.common.internal.api.dto.FileMetadata;
import com.hoo.common.internal.api.dto.UploadFileCommand;
import com.hoo.common.internal.api.dto.UploadFileResult;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.time.ZonedDateTime;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FileTestData {

    public static FileCommand defaultImageFileCommand() {

        return new FileCommand(
                new ByteArrayInputStream("image file".getBytes(UTF_8)),
                1000L,
                "image.png",
                "image/png"
        );
    }

    public static FileCommand defaultAudioFileCommand() {

        return new FileCommand(
                new ByteArrayInputStream("audio file".getBytes(UTF_8)),
                1000L,
                "sound.mp3",
                "audio/mpeg"
        );
    }


    public static UploadFileResult defaultFileResponse() {

        return new UploadFileResult(
                UuidCreator.getTimeOrderedEpoch(),
                URI.create("http://example.com/files/id"),
                ZonedDateTime.now().toEpochSecond());
    }

}
