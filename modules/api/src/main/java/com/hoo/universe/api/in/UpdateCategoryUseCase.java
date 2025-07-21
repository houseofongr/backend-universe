package com.hoo.universe.api.in;

import com.hoo.universe.api.in.dto.UpdateCategoryCommand;
import com.hoo.universe.api.in.dto.UpdateCategoryResult;

import java.util.UUID;

public interface UpdateCategoryUseCase {
    UpdateCategoryResult updateCategory(UUID categoryID, UpdateCategoryCommand command);
}
