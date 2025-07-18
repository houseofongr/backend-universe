package com.hoo.admin.application.port.out.category;

import com.hoo.admin.application.port.in.category.DeleteCategoryResult;

public interface DeleteCategoryPort {
    DeleteCategoryResult delete(Long categoryId);
}
