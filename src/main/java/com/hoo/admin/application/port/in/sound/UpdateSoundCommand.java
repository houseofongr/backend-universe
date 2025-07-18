package com.hoo.admin.application.port.in.sound;

import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;

public record UpdateSoundCommand(
        String title,
        String description,
        Boolean hidden
) {
    public UpdateSoundCommand {
        if (title != null && (title.isBlank() || title.length() > 100))
            throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);

        if (description != null && description.length() > 5000)
            throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
    }
}
