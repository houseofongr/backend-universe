package com.hoo.admin.application.port.in.space;

import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import org.springframework.web.multipart.MultipartFile;

public record CreateSpaceCommand(
        Long universeId,
        Long parentSpaceId,
        String title,
        String description,
        Float startX,
        Float startY,
        Float endX,
        Float endY,
        Boolean hidden,
        MultipartFile imageFile
) {
    public CreateSpaceCommand {
        if ((universeId == null) ||
            (title == null || title.isBlank() || title.length() > 100) ||
            (description == null || description.length() > 5000) ||
            (startX == null || startX < 0 || startX > 1) ||
            (startY == null || startY < 0 || startY > 1) ||
            (endX == null || endX < 0 || endX > 1) ||
            (endY == null || endY < 0 || endY > 1) ||
            (hidden == null)
        )
            throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
    }

    public static CreateSpaceCommand withImageFile(CreateSpaceCommand command, MultipartFile imageFile) {
        if (imageFile == null) throw new AdminException(AdminErrorCode.SPACE_FILE_REQUIRED);
        if (imageFile.getSize() > 100 * 1024 * 1024) throw new AdminException(AdminErrorCode.EXCEEDED_FILE_SIZE);

        return new CreateSpaceCommand(
                command.universeId(),
                command.parentSpaceId(),
                command.title(),
                command.description(),
                command.startX(),
                command.startY(),
                command.endX(),
                command.endY(),
                command.hidden(),
                imageFile
        );
    }
}
