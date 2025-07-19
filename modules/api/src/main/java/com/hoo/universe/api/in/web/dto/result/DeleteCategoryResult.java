package com.hoo.universe.api.in.web.dto.result;

import java.util.UUID;

public record DeleteCategoryResult(
        UUID categoryID,
        String eng,
        String kor
) {
}
