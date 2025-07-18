package com.hoo.admin.application.port.out.home;

import com.hoo.admin.domain.home.Home;

public interface UpdateHomePort {
    void updateHomeName(Home home);

    void updateMainHome(Long userId, Long homeId);
}
