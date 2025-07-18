package com.hoo.admin.application.port.in.sound;

import org.springframework.web.multipart.MultipartFile;

public interface UpdateSoundUseCase {
    UpdateSoundResult.Detail updateDetail(Long soundId, UpdateSoundCommand command);
    UpdateSoundResult.Audio updateAudio(Long soundId, MultipartFile audioFile);
}
