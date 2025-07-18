package com.hoo.admin.application.port.out.home;

import com.hoo.admin.domain.home.Home;

import java.util.List;
import java.util.Optional;

public interface FindHomePort {
    boolean exist(Long homeId);

    boolean existByHouseId(Long houseId);

    Optional<Home> loadHome(Long id);

    List<Home> loadHomes(Long userId);
}
