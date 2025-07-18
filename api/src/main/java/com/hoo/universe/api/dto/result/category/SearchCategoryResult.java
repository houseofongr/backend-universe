package com.hoo.universe.api.dto.result.category;

import com.hoo.universe.domain.vo.Category;

import java.util.List;

public record SearchCategoryResult(
        List<Category> categories
) {
}
