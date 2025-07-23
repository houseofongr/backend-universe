package com.hoo.universe.test.dto;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.internal.api.file.dto.UploadFileCommand;
import com.hoo.common.internal.api.file.dto.UploadFileResult;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.time.ZonedDateTime;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FileTestData {

    public static UploadFileCommand.FileSource defaultImageFileSource() {

        return new UploadFileCommand.FileSource(
                new ByteArrayInputStream("image file".getBytes(UTF_8)),
                "image/png",
                "image.png",
                1000L
        );
    }

    public static UploadFileCommand.FileSource defaultAudioFileSource() {

        return new UploadFileCommand.FileSource(
                new ByteArrayInputStream("audio file".getBytes(UTF_8)),
                "audio/mpeg",
                "sound.mp3",
                1000L
        );
    }


    public static UploadFileResult defaultFileResponse() {

        return new UploadFileResult(
                UuidCreator.getTimeOrderedEpoch(),
                URI.create("http://example.com/files/id"),
                ZonedDateTime.now().toEpochSecond());
    }

}
