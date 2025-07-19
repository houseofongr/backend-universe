package com.hoo.universe.api.in.web.usecase;

import com.hoo.universe.api.in.web.dto.command.UpdateCategoryCommand;
import com.hoo.universe.api.in.web.dto.result.UpdateCategoryResult;

import java.util.UUID;

public interface UpdateCategoryUseCase {
    UpdateCategoryResult updateCategory(UUID categoryID, UpdateCategoryCommand command);
}
