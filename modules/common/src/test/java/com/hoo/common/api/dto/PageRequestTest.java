package com.hoo.common.api.dto;

import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.common.web.dto.PageRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class PageRequestTest {

    @Test
    @DisplayName("offset 1 적용")
    void testOffset() {
        // given
        PageRequest pageRequest = PageRequest.of(1, 10);
        PageRequest pageRequest2 = PageRequest.of(2, 10);
        PageRequest pageRequest3 = PageRequest.of(35, 10);

        // when
        long offset = pageRequest.offset();
        long offset2 = pageRequest2.offset();
        long offset35 = pageRequest3.offset();

        // then
        assertEquals(0, offset);
        assertEquals(10, offset2);
        assertEquals(340, offset35);
    }

    @Test
    @DisplayName("페이지네이션 변환")
    void testToPagination() {
        // given
        PageRequest pageRequest = PageRequest.of(1, 10);
        PageRequest pageRequest2 = PageRequest.of(2, 10);
        List<Integer> arr = IntStream.range(0, 9).boxed().toList();

        // when
        PageQueryResult<Integer> result = PageQueryResult.from(pageRequest, 30L, arr);

        // then
        assertEquals(9, result.content().size());
        assertEquals(1, result.pagination().page());
        assertEquals(10, result.pagination().size());
        assertEquals(3, result.pagination().totalPages());
        assertEquals(30, result.pagination().totalElements());
    }

}