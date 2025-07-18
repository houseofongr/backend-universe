package com.hoo.admin.domain.home;

import com.hoo.admin.domain.house.House;
import com.hoo.admin.domain.user.User;
import com.hoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HomeTest {

    @Test
    @DisplayName("하우스와 유저로부터 홈 생성")
    void testCreateHome() throws Exception {
        // given
        House house = MockEntityFactoryService.getHouse();
        User user = MockEntityFactoryService.getUser();

        // when
        Home home = Home.create(1L, house, user);

        // then
        assertThat(home).isNotNull();
        assertThat(home.getHomeDetail().getName()).isEqualTo("leaf의 cozy house");
    }
}