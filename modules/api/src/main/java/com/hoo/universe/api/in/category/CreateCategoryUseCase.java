package com.hoo.universe.api.in.category;

import com.hoo.universe.api.dto.command.category.CreateCategoryCommand;
import com.hoo.universe.api.dto.result.category.CreateCategoryResult;

public interface CreateCategoryUseCase {
    CreateCategoryResult createNewCategory(CreateCategoryCommand command);
}
