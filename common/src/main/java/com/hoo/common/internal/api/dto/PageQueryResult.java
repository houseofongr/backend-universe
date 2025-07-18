package com.hoo.common.internal.api.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public record PageQueryResult<T>(
        List<T> content,
        Pagination pagination
) {
    public static <T> PageQueryResult<T> from(PageRequest pageRequest, Long count, List<T> entities) {
        if (count == null) count = 0L;

        int totalPages = (int) Math.ceil((double) count / pageRequest.size());

        return new PageQueryResult<>(entities, new Pagination(pageRequest.page(), pageRequest.size(), totalPages, count));
    }

    public <R> PageQueryResult<R> map(Function<? super T, ? extends R> mapper) {
        List<R> mappedContent = this.content.stream()
                .map(mapper)
                .collect(Collectors.toList());

        return new PageQueryResult<>(mappedContent, pagination);
    }

    public record pagination(
            int page,
            int size,
            int totalPages,
            long totalElements
    ) {

    }

}
