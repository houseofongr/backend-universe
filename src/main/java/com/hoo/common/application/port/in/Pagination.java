package com.hoo.common.application.port.in;

import org.springframework.data.domain.Page;

public record Pagination(
        Integer size,
        Integer pageNumber,
        Integer totalPages,
        Long totalElements
) {

    public static Pagination of(Page<?> page) {
        return new Pagination(page.getSize(),
                page.getNumber() + 1,
                page.getTotalPages(),
                page.getTotalElements());
    }

}
