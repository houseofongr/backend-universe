package com.hoo.universe.api.in.web.usecase;

import com.hoo.universe.api.in.web.dto.result.DeleteCategoryResult;

import java.util.UUID;

public interface DeleteCategoryUseCase {
    DeleteCategoryResult deleteCategory(UUID categoryID);
}
