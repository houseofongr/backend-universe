package com.hoo.admin.application.port.in.piece;

import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;

public record CreatePieceCommand(
        Long universeId,
        Long parentSpaceId,
        String title,
        String description,
        Float startX,
        Float startY,
        Float endX,
        Float endY,
        Boolean hidden
) {
    public CreatePieceCommand {
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
}
