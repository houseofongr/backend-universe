package com.hoo.admin.application.port.out.home;

import com.hoo.admin.domain.home.Home;

public interface SaveHomePort {
    Long save(Home home);
}
