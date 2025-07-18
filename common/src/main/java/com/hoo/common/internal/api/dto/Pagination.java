package com.hoo.common.internal.api.dto;

public record Pagination(
        int page,
        int size,
        int totalPages,
        long totalElements
) {

}
