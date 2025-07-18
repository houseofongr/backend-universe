package com.hoo.admin.application.service.item;

import com.hoo.admin.application.port.in.item.QueryItemResult;
import com.hoo.admin.application.port.out.item.FindItemPort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.domain.item.Item;
import com.hoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class QueryItemServiceTest {

    QueryItemService sut;

    FindItemPort findItemPort;

    @BeforeEach
    void init() {
        findItemPort = mock();
        sut = new QueryItemService(findItemPort);
    }

    @Test
    @DisplayName("아이템 쿼리 서비스 테스트")
    void testQueryItemService() throws Exception {
        // given
        Long itemId = 1L;
        Long notFoundItemId = 1234L;
        Item rectangleItem = MockEntityFactoryService.getRectangleItem();

        // when
        when(findItemPort.loadItem(itemId)).thenReturn(Optional.of(rectangleItem));
        QueryItemResult result = sut.queryItem(itemId);

        // then
        assertThatThrownBy(() -> sut.queryItem(notFoundItemId)).hasMessage(AdminErrorCode.ITEM_NOT_FOUND.getMessage());
        assertThat(result.itemName()).isEqualTo("설이");
    }
}