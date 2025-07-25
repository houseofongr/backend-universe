package com.hoo.universe.api.out;

import java.util.UUID;

public interface UpdateCategoryPort {
    void saveCategory(UUID uuid, String kor, String eng);
    void deleteCategory(UUID categoryID);
    void updateCategory(UUID categoryID, String kor, String eng);
}
