package com.hoo.universe.api.in.category;

import com.hoo.universe.api.dto.command.category.UpdateCategoryCommand;
import com.hoo.universe.api.dto.result.category.UpdateCategoryResult;

import java.util.UUID;

public interface UpdateCategoryUseCase {
    UpdateCategoryResult updateCategory(UUID categoryID, UpdateCategoryCommand command);
}
