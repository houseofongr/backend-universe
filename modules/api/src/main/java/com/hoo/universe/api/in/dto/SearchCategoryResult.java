package com.hoo.universe.api.in.dto;

import com.hoo.universe.domain.vo.Category;

import java.util.List;

public record SearchCategoryResult(
        List<Category> categories
) {
}
