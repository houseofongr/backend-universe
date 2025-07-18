package com.hoo.universe.api.out.persistence;

import java.util.UUID;

public interface CommandCategoryPort {
    void saveNewCategory(UUID uuid, String kor, String eng);
    void deleteCategory(UUID categoryID);
    void updateCategory(UUID categoryID, String kor, String eng);
}
