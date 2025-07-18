package com.hoo.admin.application.port.in.category;

public record UpdateCategoryResult(
        String message,
        Long categoryId,
        String eng,
        String kor
) {
}
