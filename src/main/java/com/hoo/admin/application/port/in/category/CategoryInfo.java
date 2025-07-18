package com.hoo.admin.application.port.in.category;

import com.hoo.admin.domain.universe.UniverseCategory;

public record CategoryInfo(
        Long id,
        String eng,
        String kor
) {

    public static CategoryInfo of(UniverseCategory category) {
        return new CategoryInfo(
                category.getId(),
                category.getEng(),
                category.getKor()
        );
    }
}
