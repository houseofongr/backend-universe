package com.hoo.admin.application.port.out.category;

import com.hoo.admin.application.port.in.category.SearchCategoryResult;
import com.hoo.admin.domain.universe.UniverseCategory;

public interface FindCategoryPort {
    SearchCategoryResult findAll();
    UniverseCategory findUniverseCategory(Long categoryId);
}
