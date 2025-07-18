package com.hoo.admin.application.port.in.category;

public interface UpdateCategoryUseCase {
    UpdateCategoryResult update(Long categoryId, UpdateCategoryCommand command);
}
