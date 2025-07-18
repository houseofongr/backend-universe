package com.hoo.admin.application.port.in.category;

import java.util.List;

public record SearchCategoryResult(
        List<CategoryInfo> categories
) {
}
