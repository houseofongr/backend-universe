package com.hoo.admin.application.port.in.category;

public record DeleteCategoryResult(
        String message,
        Long deletedCategoryId
) {
    public static DeleteCategoryResult of(Long id) {
        return new DeleteCategoryResult(
                String.format("[#%d]번 카테고리가 삭제되었습니다.", id),
                id
        );
    }
}
