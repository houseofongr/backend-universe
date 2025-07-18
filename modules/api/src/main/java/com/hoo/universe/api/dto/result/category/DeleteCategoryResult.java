package com.hoo.universe.api.dto.result.category;

import java.util.UUID;

public record DeleteCategoryResult(
        UUID categoryID,
        String eng,
        String kor
) {
}
