package com.hoo.admin.application.port.out.category;

import com.hoo.admin.application.port.in.category.UpdateCategoryResult;

public interface UpdateCategoryPort {
    UpdateCategoryResult update(Long categoryId, String kor, String eng);
}
