package com.hoo.admin.application.port.in.sound;

import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import org.springframework.web.multipart.MultipartFile;

public record CreateSoundCommand(
        Long pieceId,
        String title,
        String description,
        Boolean hidden,
        MultipartFile audioFile
) {
    public CreateSoundCommand {
        if ((pieceId == null) ||
            (title == null || title.isBlank() || title.length() > 100) ||
            (description != null && description.length() > 5000) ||
            (hidden == null)
        )
            throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
    }

    public static CreateSoundCommand withAudioFile(CreateSoundCommand baseCommand, MultipartFile audioFile) {
        if (audioFile == null) throw new AdminException(AdminErrorCode.SOUND_FILE_REQUIRED);
        if (audioFile.getSize() > 100 * 1024 * 1024) throw new AdminException(AdminErrorCode.EXCEEDED_FILE_SIZE);

        return new CreateSoundCommand(
                baseCommand.pieceId(),
                baseCommand.title(),
                baseCommand.description(),
                baseCommand.hidden(),
                audioFile
        );
    }
}
