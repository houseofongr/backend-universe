package com.hoo.admin.application.port.in.space;

import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;

public record UpdateSpaceCommand() {

    public record Detail(
            String title,
            String description,
            Boolean hidden
    ) {
        public Detail {
            if ((title != null && (title.isBlank() || title.length() > 100)) ||
                (description != null && description.length() > 5000))
                throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        }
    }

    public record Position(
            Float startX,
            Float startY,
            Float endX,
            Float endY
    ) {
        public Position {
            if ((startX != null && (startX < 0 || startX > 1)) ||
                (startY != null && (startY < 0 || startY > 1)) ||
                (endX != null && (endX < 0 || endX > 1)) ||
                (endY != null && (endY < 0 || endY > 1))
            ) throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        }
    }
}
