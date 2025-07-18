package com.hoo.common.web.dto;

public record Pagination(
        int page,
        int size,
        int totalPages,
        long totalElements
) {

}
