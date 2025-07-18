package com.hoo.admin.application.service.home;

import com.hoo.admin.application.port.in.home.QueryHomeResult;
import com.hoo.admin.application.port.in.home.QueryHomeUseCase;
import com.hoo.admin.application.port.in.home.QueryUserHomesResult;
import com.hoo.admin.application.port.in.home.QueryUserHomesUseCase;
import com.hoo.admin.application.port.out.home.FindHomePort;
import com.hoo.admin.application.port.out.house.FindHousePort;
import com.hoo.admin.application.port.out.user.FindUserPort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import com.hoo.admin.domain.home.Home;
import com.hoo.admin.domain.house.House;
import com.hoo.admin.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryHomeService implements QueryHomeUseCase, QueryUserHomesUseCase {

    private final FindHomePort findHomePort;
    private final FindHousePort findHousePort;
    private final FindUserPort findUserPort;

    @Override
    @Transactional(readOnly = true)
    public QueryHomeResult queryHome(Long id) {

        Home home = findHomePort.loadHome(id)
                .orElseThrow(() -> new AdminException(AdminErrorCode.HOME_NOT_FOUND));

        House house = findHousePort.load(home.getHouseId().getId())
                .orElseThrow(() -> new AdminException(AdminErrorCode.HOUSE_NOT_FOUND));

        User user = findUserPort.loadUser(home.getOwnerId())
                .orElseThrow(() -> new AdminException(AdminErrorCode.USER_NOT_FOUND));

        return QueryHomeResult.of(home, house, user);

    }

    @Override
    @Transactional(readOnly = true)
    public QueryUserHomesResult queryUserHomes(Long userId) {

        List<Home> homes = findHomePort.loadHomes(userId);

        List<QueryUserHomesResult.HomeInfo> homeInfos = new ArrayList<>();
        for (Home home : homes) {

            House house = findHousePort.load(home.getHouseId().getId())
                    .orElseThrow(() -> new AdminException(AdminErrorCode.HOUSE_NOT_FOUND));

            User user = findUserPort.loadUser(home.getOwnerId())
                    .orElseThrow(() -> new AdminException(AdminErrorCode.USER_NOT_FOUND));

            homeInfos.add(QueryUserHomesResult.HomeInfo.of(home, house, user));
        }

        return new QueryUserHomesResult(homeInfos);
    }

}
