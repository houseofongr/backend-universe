package com.hoo.admin.application.port.out.category;

import com.hoo.admin.application.port.in.category.CreateCategoryResult;

public interface SaveCategoryPort {
    CreateCategoryResult save(String kor, String eng);
}
