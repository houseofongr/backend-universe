package com.hoo.universe.api.in.web.dto.result;

import java.util.UUID;

public record CreateCategoryResult(
        UUID categoryID,
        String eng,
        String kor
) {
}
