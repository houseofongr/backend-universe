package com.hoo.universe.application;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.enums.AccessLevel;
import com.hoo.common.internal.api.user.GetUserInfoAPI;
import com.hoo.common.internal.api.user.dto.UserInfo;
import com.hoo.universe.api.in.dto.UpdateUniverseMetadataCommand;
import com.hoo.universe.api.out.LoadUniversePort;
import com.hoo.universe.api.out.QueryCategoryPort;
import com.hoo.universe.api.out.UpdateUniverseStatusPort;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.vo.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverseOnly;
import static org.mockito.Mockito.*;

class UpdateUniverseMetadataServiceTest {

    LoadUniversePort loadUniversePort = mock();
    GetUserInfoAPI getOwnerAPI = mock();
    QueryCategoryPort queryCategoryPort = mock();
    UpdateUniverseStatusPort updateUniverseStatusPort = mock();

    UpdateUniverseMetadataService sut = new UpdateUniverseMetadataService(loadUniversePort, getOwnerAPI, queryCategoryPort, updateUniverseStatusPort);

    @Test
    @DisplayName("정보 수정 서비스")
    void testUpdateDetail() {
        // given
        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        UpdateUniverseMetadataCommand command = new UpdateUniverseMetadataCommand("오르트구름", "오르트구름은 태양계 최외곽에 위치하고 있습니다.", UuidCreator.getTimeOrderedEpoch(), UuidCreator.getTimeOrderedEpoch(), AccessLevel.PRIVATE.name(), List.of("오르트구름", "태양계", "윤하", "별"));
        Universe universe = defaultUniverseOnly()
                .withUniverseID(new Universe.UniverseID(universeID))
                .build();
        Owner newOwner = new Owner(UuidCreator.getTimeOrderedEpoch(), "leaffael");

        // when
        when(loadUniversePort.loadUniverseOnly(universeID)).thenReturn(universe);
        when(getOwnerAPI.getUserInfo(command.ownerID())).thenReturn(new UserInfo(command.ownerID(), true, true, "test@example.com", "leaf", "BUSINESS", "ACTIVATE", ZonedDateTime.now().toEpochSecond()));
        sut.updateUniverseMetadata(universeID, command);

        // then
        verify(updateUniverseStatusPort, times(1)).updateUniverseMetadata(any());
    }
}