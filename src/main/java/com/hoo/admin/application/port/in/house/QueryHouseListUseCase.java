package com.hoo.admin.application.port.in.house;

public interface QueryHouseListUseCase {
    QueryHouseListResult query(QueryHouseListCommand command);
}
