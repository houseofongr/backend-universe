package com.hoo.common.adapter.out.persistence.entity;

import com.hoo.aar.adapter.out.persistence.mapper.SnsAccountMapper;
import com.hoo.common.adapter.out.persistence.repository.SnsAccountJpaRepository;
import com.hoo.common.application.service.MockEntityFactoryService;
import org.assertj.core.data.TemporalUnitWithinOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DateColumnBaseEntityTest {

    @Autowired
    SnsAccountJpaRepository repository;

    SnsAccountMapper mapper = new SnsAccountMapper();

    @Test
    @DisplayName("DB 시간과 동기화 확인")
    void testSyncTime() {
        SnsAccountJpaEntity entity = SnsAccountJpaEntity.create(MockEntityFactoryService.getSnsAccount());
        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());

        repository.save(entity);

        SnsAccountJpaEntity snsAccountJpaEntity = repository.findById(entity.getId()).get();

        assertThat(snsAccountJpaEntity.getCreatedTime()).isCloseTo(now, new TemporalUnitWithinOffset(1, ChronoUnit.SECONDS));
        assertThat(snsAccountJpaEntity.getUpdatedTime()).isCloseTo(now, new TemporalUnitWithinOffset(1, ChronoUnit.SECONDS));
    }

}