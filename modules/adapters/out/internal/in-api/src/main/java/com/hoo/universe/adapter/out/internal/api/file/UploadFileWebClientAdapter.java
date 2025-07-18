package com.hoo.universe.adapter.out.internal.api.file;

import com.hoo.common.internal.api.UploadFileAPI;
import com.hoo.common.internal.api.dto.UploadFileCommand;
import com.hoo.common.internal.api.dto.UploadFileResult;
import com.hoo.universe.adapter.out.internal.api.InternalAPIConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class UploadFileWebClientAdapter implements UploadFileAPI {

    private final WebClient webClient;
    private final InternalAPIConfigProperties internalAPIConfigProperties;

    @Override
    public UploadFileResult uploadFile(UploadFileCommand uploadFileCommand) {

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder
                .part("file", new InputStreamResource(uploadFileCommand.inputStream()))
                .filename(uploadFileCommand.fileMetadata().name())
                .header("Content-Disposition", "form-data; name=file; filename=\"" + uploadFileCommand.fileMetadata().name() + "\"");

        return webClient.post()
                .uri(internalAPIConfigProperties.getFile().getUploadFileUrl())
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(UploadFileResult.class)
                .block();
    }
}
