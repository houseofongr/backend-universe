package com.hoo.universe.test.dto;


import com.hoo.common.internal.api.dto.PageRequest;

public class PageableTestData {

    public static PageRequest defaultPageable() {
        return new PageRequest(1, 10, null, null, "title", false);
    }

    public static PageRequest defaultPageable(Integer offset, Integer size) {
        return new PageRequest(offset, size, null, null, "title", false);
    }
}
