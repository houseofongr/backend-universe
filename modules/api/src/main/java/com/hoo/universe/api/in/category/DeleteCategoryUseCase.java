package com.hoo.universe.api.in.category;

import com.hoo.universe.api.dto.result.category.DeleteCategoryResult;

import java.util.UUID;

public interface DeleteCategoryUseCase {
    DeleteCategoryResult deleteCategory(UUID categoryID);
}
