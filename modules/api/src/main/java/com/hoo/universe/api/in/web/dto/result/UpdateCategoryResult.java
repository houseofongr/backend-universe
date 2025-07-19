package com.hoo.universe.api.in.web.dto.result;

import java.util.UUID;

public record UpdateCategoryResult(
        UUID categoryID,
        String eng,
        String kor
) {
}
