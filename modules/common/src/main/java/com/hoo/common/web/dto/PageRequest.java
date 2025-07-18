package com.hoo.common.web.dto;

public record PageRequest(
        int page,
        int size,
        String searchType,
        String keyword,
        String sortType,
        Boolean isAsc
) {

    public long offset() {
        return (long) (page - 1) * size;
    }

    public static PageRequest defaultPage() {
        return new PageRequest(1, 10, null, null, null, null);
    }

    public static PageRequest of(int page, int size) {
        return new PageRequest(page, size, null, null, null, null);
    }

    public static PageRequest ofWithDefault(String searchType, String keyword, String sortType, Boolean isAsc) {
        return new PageRequest(1, 10, searchType, keyword, sortType, isAsc);
    }

    public static PageRequest ofWithKeyword(String searchType, String keyword) {
        return PageRequest.ofWithDefault(searchType, keyword, null, null);
    }

    public static PageRequest ofWithSort(String sortType, Boolean isAsc) {
        return PageRequest.ofWithDefault(null, null, sortType, isAsc);
    }

}
