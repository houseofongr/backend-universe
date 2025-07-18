package com.hoo.admin.application.port.in.house;

import com.hoo.common.adapter.out.persistence.entity.HouseJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class QueryHouseInfoListResultTest {

    @Test
    @DisplayName("하우스 JPA 엔티티로부터 DTO 생성")
    void testCreateHouseDto() {
        // given
        String description = "this is new house. this is new house. this is new house. this is new house. this is new house. this is new house. this is new house. this is new house. this is new house.";
        HouseJpaEntity houseJpaEntity = new HouseJpaEntity(1L, "new house", "leaf", description, 5000F, 5000F, 1L, 2L, null);

        // when
        houseJpaEntity.prePersist();
        QueryHouseListResult.HouseInfo houseInfoDto = QueryHouseListResult.HouseInfo.of(houseJpaEntity);

        // then
        assertThat(houseInfoDto.id()).isEqualTo(houseJpaEntity.getId());
        assertThat(houseInfoDto.title()).isEqualTo(houseJpaEntity.getTitle());
        assertThat(houseInfoDto.author()).isEqualTo(houseJpaEntity.getAuthor());
        assertThat(houseInfoDto.description()).isEqualTo(houseJpaEntity.getDescription().substring(0, 100) + "...");
        assertThat(houseInfoDto.imageId()).isEqualTo(houseJpaEntity.getBasicImageFileId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM.dd. yyyy", Locale.ENGLISH);
        assertThat(houseInfoDto.createdDate()).isEqualTo(formatter.format(houseJpaEntity.getCreatedTime()));
        assertThat(houseInfoDto.updatedDate()).isEqualTo(formatter.format(houseJpaEntity.getUpdatedTime()));

    }

    @Test
    @DisplayName("생성 시 Description 100글자에서 자르는지 확인")
    void testDescription100() {
        // given
        char[] _100Description = new char[100];
        Arrays.fill(_100Description, 'a');

        char[] _101Description = new char[101];
        Arrays.fill(_101Description, 'a');

        HouseJpaEntity shortDescription = new HouseJpaEntity(1L, "new house", "leaf", "this is short.", 5000F, 5000F, 1L, 2L, null);
        HouseJpaEntity fitDescription = new HouseJpaEntity(1L, "new house", "leaf", new String(_100Description), 5000F, 5000F, 1L, 2L, null);
        HouseJpaEntity overDescription = new HouseJpaEntity(1L, "new house", "leaf", new String(_101Description), 5000F, 5000F, 1L, 2L, null);

        // when
        shortDescription.prePersist();
        fitDescription.prePersist();
        overDescription.prePersist();

        QueryHouseListResult.HouseInfo houseInfoDto = QueryHouseListResult.HouseInfo.of(shortDescription);
        QueryHouseListResult.HouseInfo houseInfoDto2 = QueryHouseListResult.HouseInfo.of(fitDescription);
        QueryHouseListResult.HouseInfo houseInfoDto3 = QueryHouseListResult.HouseInfo.of(overDescription);

        // then
        assertThat(houseInfoDto.description()).isEqualTo(shortDescription.getDescription());
        assertThat(houseInfoDto2.description()).isEqualTo(fitDescription.getDescription());
        assertThat(houseInfoDto3.description()).isEqualTo(overDescription.getDescription().substring(0, 100) + "...");
    }
}