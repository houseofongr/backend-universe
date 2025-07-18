package com.hoo.admin.application.port.out.house;

import com.hoo.admin.application.port.in.house.QueryHouseListCommand;
import com.hoo.admin.application.port.in.house.QueryHouseListResult;
import com.hoo.admin.domain.house.House;

import java.util.Optional;

public interface FindHousePort {
    Optional<House> load(Long id);

    QueryHouseListResult search(QueryHouseListCommand command);
}
