package com.hoo.universe.api.out.persistence;

import com.hoo.universe.domain.vo.Category;

import java.util.List;
import java.util.UUID;

public interface QueryCategoryPort {
    List<Category> findAllCategories();

    Category findUniverseCategory(UUID categoryID);
}
