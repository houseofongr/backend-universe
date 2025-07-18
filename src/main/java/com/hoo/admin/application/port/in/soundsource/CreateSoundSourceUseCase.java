package com.hoo.admin.application.port.in.soundsource;

import org.springframework.web.multipart.MultipartFile;

public interface CreateSoundSourceUseCase {
    CreateSoundSourceResult createSoundSource(Long itemId, CreateSoundSourceMetadata metadata, MultipartFile soundFile);
}
