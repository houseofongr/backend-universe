package com.hoo.universe.api.in;

import com.hoo.universe.api.in.dto.DeleteCategoryResult;

import java.util.UUID;

public interface DeleteCategoryUseCase {
    DeleteCategoryResult deleteCategory(UUID categoryID);
}
