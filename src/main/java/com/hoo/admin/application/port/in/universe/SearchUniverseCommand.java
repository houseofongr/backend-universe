package com.hoo.admin.application.port.in.universe;

import org.springframework.data.domain.Pageable;

public record SearchUniverseCommand(
        Pageable pageable,
        Long categoryId,
        String searchType,
        String keyword,
        String sortType,
        Boolean isAsc
) {
}
