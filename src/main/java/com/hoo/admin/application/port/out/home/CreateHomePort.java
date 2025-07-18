package com.hoo.admin.application.port.out.home;

import com.hoo.admin.domain.home.Home;
import com.hoo.admin.domain.house.House;
import com.hoo.admin.domain.user.User;

public interface CreateHomePort {
    Home createHome(House house, User user);
}
