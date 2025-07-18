package com.hoo.universe.application;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.api.dto.command.UpdateUniverseMetadataCommand;
import com.hoo.universe.api.out.persistence.LoadUniversePort;
import com.hoo.universe.api.out.persistence.HandleUniverseEventPort;
import com.hoo.universe.api.out.internal.FindAuthorAPI;
import com.hoo.universe.api.out.persistence.QueryCategoryPort;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.vo.AccessStatus;
import com.hoo.universe.domain.vo.Author;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverseOnly;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UpdateUniverseMetadataServiceTest {

    LoadUniversePort loadUniversePort = mock();
    FindAuthorAPI findAuthorAPI = mock();
    QueryCategoryPort queryCategoryPort = mock();
    HandleUniverseEventPort handleUniverseEventPort = mock();

    UpdateUniverseMetadataService sut = new UpdateUniverseMetadataService(loadUniversePort, findAuthorAPI, queryCategoryPort, handleUniverseEventPort);

    @Test
    @DisplayName("정보 수정 서비스")
    void testUpdateDetail() {
        // given
        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        UpdateUniverseMetadataCommand command = new UpdateUniverseMetadataCommand("오르트구름", "오르트구름은 태양계 최외곽에 위치하고 있습니다.", UuidCreator.getTimeOrderedEpoch(), UuidCreator.getTimeOrderedEpoch(), AccessStatus.PRIVATE.name(), List.of("오르트구름", "태양계", "윤하", "별"));
        Universe universe = defaultUniverseOnly()
                .withUniverseID(new Universe.UniverseID(universeID))
                .build();
        Author newAuthor = new Author(UuidCreator.getTimeOrderedEpoch(), "leaffael");

        // when
        when(loadUniversePort.loadUniverseOnly(universeID)).thenReturn(universe);
        when(findAuthorAPI.findAuthor(command.authorID())).thenReturn(newAuthor);
        sut.updateUniverseMetadata(universeID, command);

        // then
        verify(handleUniverseEventPort, times(1)).handleUniverseMetadataUpdateEvent(any());
    }
}