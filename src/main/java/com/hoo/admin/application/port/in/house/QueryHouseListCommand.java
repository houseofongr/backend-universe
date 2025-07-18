package com.hoo.admin.application.port.in.house;

import com.hoo.common.adapter.out.persistence.condition.HouseSearchType;
import org.springframework.data.domain.Pageable;

public record QueryHouseListCommand(
        Pageable pageable,
        HouseSearchType searchType,
        String keyword
) {
}
