package com.hoo.universe.api.in.web.usecase;

import com.hoo.universe.api.in.web.dto.command.CreateCategoryCommand;
import com.hoo.universe.api.in.web.dto.result.CreateCategoryResult;

public interface CreateCategoryUseCase {
    CreateCategoryResult createNewCategory(CreateCategoryCommand command);
}
