package com.hoo.admin.application.service.house;


import com.hoo.admin.application.port.in.house.*;
import com.hoo.admin.application.port.out.house.FindHousePort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import com.hoo.admin.domain.house.House;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryHouseService implements QueryHouseListUseCase, QueryHouseUseCase {

    private final FindHousePort findHousePort;

    @Override
    @Transactional(readOnly = true)
    public QueryHouseResult queryHouse(Long id) {

        House house = findHousePort.load(id)
                .orElseThrow(() -> new AdminException(AdminErrorCode.HOUSE_NOT_FOUND));

        return QueryHouseResult.of(house);
    }

    @Override
    @Transactional(readOnly = true)
    public QueryHouseListResult query(QueryHouseListCommand command) {
        return findHousePort.search(command);
    }

}
