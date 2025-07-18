package com.hoo.common.adapter.out.persistence.repository;

import com.hoo.admin.application.port.in.house.QueryHouseListCommand;
import com.hoo.common.adapter.out.persistence.entity.HouseJpaEntity;
import org.springframework.data.domain.Page;

public interface HouseQueryDslRepository {
    Page<HouseJpaEntity> searchByCommand(QueryHouseListCommand command);
}
