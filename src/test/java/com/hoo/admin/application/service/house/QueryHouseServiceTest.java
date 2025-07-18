package com.hoo.admin.application.service.house;

import com.hoo.admin.application.port.in.house.QueryHouseListResult;
import com.hoo.admin.application.port.in.house.QueryHouseResult;
import com.hoo.admin.application.port.out.house.FindHousePort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import com.hoo.common.application.port.in.Pagination;
import com.hoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class QueryHouseServiceTest {

    QueryHouseService sut;

    FindHousePort findHousePort;

    @BeforeEach
    void init() {
        findHousePort = mock();
        sut = new QueryHouseService(findHousePort);
    }

    @Test
    @DisplayName("하우스 리스트 조회 서비스 테스트")
    void testQueryHouseListService() {
        // given

        QueryHouseListResult result = new QueryHouseListResult(List.of(
                new QueryHouseListResult.HouseInfo(null, null, null, null, null, null, null),
                new QueryHouseListResult.HouseInfo(null, null, null, null, null, null, null),
                new QueryHouseListResult.HouseInfo(null, null, null, null, null, null, null),
                new QueryHouseListResult.HouseInfo(null, null, null, null, null, null, null),
                new QueryHouseListResult.HouseInfo(null, null, null, null, null, null, null),
                new QueryHouseListResult.HouseInfo(null, null, null, null, null, null, null),
                new QueryHouseListResult.HouseInfo(null, null, null, null, null, null, null),
                new QueryHouseListResult.HouseInfo(null, null, null, null, null, null, null),
                new QueryHouseListResult.HouseInfo(null, null, null, null, null, null, null)
        ), new Pagination(1, 1, 1, 1L));

        // when
        when(findHousePort.search(any())).thenReturn(result);
        QueryHouseListResult list = sut.query(any());

        // then
        assertThat(list.houses()).hasSize(9);
    }

    @Test
    @DisplayName("하우스 단건 조회 서비스 테스트")
    void testQueryHouseService() throws Exception {
        // given
        Long houseId = 1L;

        // when
        when(findHousePort.load(houseId)).thenReturn(Optional.of(MockEntityFactoryService.loadHouse()));
        QueryHouseResult result = sut.queryHouse(houseId);

        // then
        assertThat(result).isNotNull();

        // 조회되지 않을 때 예외처리
        assertThatThrownBy(() -> sut.queryHouse(2L)).isInstanceOf(AdminException.class)
                .hasMessage(AdminErrorCode.HOUSE_NOT_FOUND.getMessage());
    }

}