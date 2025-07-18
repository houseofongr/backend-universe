package com.hoo.admin.application.service.house;

import com.hoo.admin.application.port.in.house.UpdateHouseInfoCommand;
import com.hoo.admin.application.port.out.house.FindHousePort;
import com.hoo.admin.application.port.out.house.UpdateHousePort;
import com.hoo.admin.domain.house.House;
import com.hoo.common.application.port.in.MessageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.hoo.common.application.service.MockEntityFactoryService.getHouse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UpdateHouseServiceTest {

    UpdateHouseService sut;

    FindHousePort findHousePort;
    UpdateHousePort updateHousePort;

    @BeforeEach
    void init() {
        findHousePort = mock();
        updateHousePort = mock();
        sut = new UpdateHouseService(findHousePort, updateHousePort);
    }

    @Test
    @DisplayName("하우스 업데이트 서비스 테스트")
    void testUpdateHouseInfo() throws Exception {
        // given
        House house = getHouse();
        UpdateHouseInfoCommand command = new UpdateHouseInfoCommand(1L, "not cozy house", "arang", "this is not cozy house.");

        // when
        when(findHousePort.load(1L)).thenReturn(Optional.of(house));
        MessageDto message = sut.update(command);

        // then
        verify(findHousePort, times(1)).load(1L);
        verify(updateHousePort, times(1)).update(any());

        assertThat(message.message()).isEqualTo("1번 하우스 정보 수정이 완료되었습니다.");
    }


}