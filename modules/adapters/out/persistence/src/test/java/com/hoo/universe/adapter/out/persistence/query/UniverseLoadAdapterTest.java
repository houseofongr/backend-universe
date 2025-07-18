package com.hoo.universe.adapter.out.persistence.query;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoo.universe.adapter.out.persistence.PersistenceAdapterTest;
import com.hoo.universe.adapter.out.persistence.repository.UniverseJpaRepository;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.vo.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("classpath:sql/universe.sql")
@PersistenceAdapterTest
class UniverseLoadAdapterTest {

    @Autowired
    UniverseLoadAdapter sut;

    @Autowired
    UniverseJpaRepository universeJpaRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("직렬화 / 역직렬화 테스트")
    void deserialize() throws JsonProcessingException {
        String json = "[ [0.8, 0.1], [0.2, 0.1], [0.2, 0.4], [0.8, 0.4] ]";

        List<Point> points = objectMapper.readValue(json, new TypeReference<>() {
        });

        assertThat(points).hasSize(4);
    }

    @Test
    @DisplayName("유니버스만 조회")
    void loadUniverseWithAllEntityOnly() {
        // given
        UUID universeID = universeJpaRepository.findUuidById(1L);

        // when
        Universe universe = sut.loadUniverseOnly(universeID);

        // then
        assertThat(universe.getId().uuid()).isEqualTo(universeID);
        assertThat(universe.getCommonMetadata().getTitle()).isEqualTo("정책 유니버스");
        assertThat(universe.getSpaces()).hasSize(0);
        assertThat(universe.getPieces()).hasSize(0);
    }

    @Test
    @DisplayName("사운드 제외하고 조회")
    void loadUniverseWithAllEntityExceptSound() {
        // given
        UUID universeID = universeJpaRepository.findUuidById(1L);

        // when
        Universe universe = sut.loadUniverseExceptSounds(universeID);

        // then
        assertThat(universe.getId().uuid()).isEqualTo(universeID);
        assertThat(universe.getCommonMetadata().getTitle()).isEqualTo("정책 유니버스");
        assertThat(universe.getSpaces()).hasSize(2);
        assertThat(universe.getPieces()).hasSize(1);
    }

    @Test
    @DisplayName("유니버스 전체 조회")
    void loadUniverseWithAllEntity() {
        // given
        UUID universeID = universeJpaRepository.findUuidById(1L);

        // when
        Universe universe = sut.loadUniverseWithAllEntity(universeID);

        // then
        assertThat(universe.getId().uuid()).isEqualTo(universeID);
        assertThat(universe.getCommonMetadata().getTitle()).isEqualTo("정책 유니버스");
        assertThat(universe.getSpaces()).hasSize(2);
        assertThat(universe.getPieces()).hasSize(1);
        assertThat(universe
                .getSpaces().getLast() // 스페이스 2
                .getSpaces().getFirst() // 스페이스 4
                .getPieces().getFirst() // 피스 4
                .getSounds()).hasSize(4);
    }

}