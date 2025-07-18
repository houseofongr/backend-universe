package com.hoo.admin.adapter.in.web.soundsource;

import com.hoo.admin.application.port.in.soundsource.CreateSoundSourceMetadata;
import com.hoo.admin.application.port.in.soundsource.CreateSoundSourceResult;
import com.hoo.admin.application.port.in.soundsource.CreateSoundSourceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import static com.hoo.common.util.GsonUtil.gson;

@RestController
@RequiredArgsConstructor
public class PostSoundSourceController {

    private final CreateSoundSourceUseCase createSoundSourceUseCase;

    @PostMapping("/admin/items/{itemId}/sound-sources")
    public ResponseEntity<CreateSoundSourceResult> createSoundSource(
            @PathVariable Long itemId,
            @RequestParam String metadata,
            MultipartHttpServletRequest request
    ) {

        CreateSoundSourceMetadata soundSourceMetadata = gson.fromJson(metadata, CreateSoundSourceMetadata.class);
        MultipartFile soundFile = request.getFile("soundFile");

        return new ResponseEntity<>(createSoundSourceUseCase.createSoundSource(itemId, soundSourceMetadata, soundFile), HttpStatus.CREATED);
    }
}
