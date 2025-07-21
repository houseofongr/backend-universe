package com.hoo.universe.api.in;

import com.hoo.universe.api.in.dto.CreateCategoryCommand;
import com.hoo.universe.api.in.dto.CreateCategoryResult;

public interface CreateCategoryUseCase {
    CreateCategoryResult createNewCategory(CreateCategoryCommand command);
}
