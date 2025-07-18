package com.hoo.admin.application.service.item;

import com.hoo.admin.application.port.in.item.QueryItemResult;
import com.hoo.admin.application.port.in.item.QueryItemUseCase;
import com.hoo.admin.application.port.out.item.FindItemPort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import com.hoo.admin.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryItemService implements QueryItemUseCase {

    private final FindItemPort findItemPort;

    @Override
    @Transactional(readOnly = true)
    public QueryItemResult queryItem(Long id) {
        Item item = findItemPort.loadItem(id)
                .orElseThrow(() -> new AdminException(AdminErrorCode.ITEM_NOT_FOUND));

        return QueryItemResult.of(item);
    }
}
