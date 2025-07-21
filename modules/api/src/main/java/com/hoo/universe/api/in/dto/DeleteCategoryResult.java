package com.hoo.universe.api.in.dto;

import java.util.UUID;

public record DeleteCategoryResult(
        UUID categoryID,
        String eng,
        String kor
) {
}
