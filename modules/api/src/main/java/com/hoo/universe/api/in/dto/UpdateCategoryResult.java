package com.hoo.universe.api.in.dto;

import java.util.UUID;

public record UpdateCategoryResult(
        UUID categoryID,
        String eng,
        String kor
) {
}
