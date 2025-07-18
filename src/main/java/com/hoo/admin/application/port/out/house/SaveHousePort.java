package com.hoo.admin.application.port.out.house;

import com.hoo.admin.domain.house.House;

public interface SaveHousePort {
    Long save(House house);
}
