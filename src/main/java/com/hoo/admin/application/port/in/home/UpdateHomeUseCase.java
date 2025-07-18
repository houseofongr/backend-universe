package com.hoo.admin.application.port.in.home;

public interface UpdateHomeUseCase {
    void updateHomeName(Long homeId, String homeName);

    void updateMainHome(Long userId, Long homeId);
}
