package com.hoo.universe.adapter.out.internal.api.file;

import com.hoo.common.internal.api.file.UploadFileAPI;
import com.hoo.common.internal.api.file.dto.UploadFileCommand;
import com.hoo.common.internal.api.file.dto.UploadFileResult;
import com.hoo.universe.adapter.out.internal.api.InternalProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class UploadFileWebClientAdapter implements UploadFileAPI {

    private final WebClient webClient;
    private final InternalProperties internalProperties;

    @Override
    public UploadFileResult uploadFile(UploadFileCommand uploadFileCommand) {

        MultipartBodyBuilder body = new MultipartBodyBuilder();

        body.part("file", new InputStreamResource(uploadFileCommand.fileSource().inputStream()))
                .filename(uploadFileCommand.fileSource().name())
                .contentType(MediaType.parseMediaType(uploadFileCommand.fileSource().contentType()));

        body.part("metadata", uploadFileCommand.metadata());

        return webClient.post()
                .uri(internalProperties.getFile().getUploadFileUrl())
                .body(BodyInserters.fromMultipartData(body.build()))
                .retrieve()
                .bodyToMono(UploadFileResult.class)
                .block();
    }
}
