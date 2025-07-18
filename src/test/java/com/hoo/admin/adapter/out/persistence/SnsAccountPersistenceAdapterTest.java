package com.hoo.admin.adapter.out.persistence;

import com.hoo.aar.adapter.out.persistence.mapper.SnsAccountMapper;
import com.hoo.common.adapter.out.persistence.repository.SnsAccountJpaRepository;
import com.hoo.admin.domain.user.snsaccount.SnsAccount;
import com.hoo.common.adapter.out.persistence.PersistenceAdapterTest;
import com.hoo.common.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@PersistenceAdapterTest
@Import({SnsAccountPersistenceAdapter.class, SnsAccountMapper.class})
class SnsAccountPersistenceAdapterTest {

    @Autowired
    SnsAccountPersistenceAdapter sut;

    @Autowired
    SnsAccountJpaRepository repository;

    @Autowired
    private SnsAccountMapper snsAccountMapper;

    @Test
    @Sql("SnsAccountPersistenceAdapterTest.sql")
    @DisplayName("SNS Account 조회")
    void testFindSnsAccount() {
        // given
        SnsAccount snsAccount = MockEntityFactoryService.getSnsAccount();

        // when
        Optional<SnsAccount> entityById = sut.loadSnsAccount(1L);
        Optional<SnsAccount> entityBySnsId = sut.loadSnsAccount(snsAccount.getSnsAccountId().getSnsDomain(), snsAccount.getSnsAccountId().getSnsId());

        // then
        assertThat(entityById).isNotEmpty();
        assertThat(entityBySnsId).isNotEmpty();
        assertThat(entityById.get()).usingRecursiveComparison()
                .ignoringFields("snsAccountId.persistenceId", "baseTime", "userId").isEqualTo(snsAccount);
        assertThat(entityBySnsId.get()).usingRecursiveComparison()
                .ignoringFields("snsAccountId.persistenceId", "baseTime", "userId").isEqualTo(snsAccount);
    }

    @Test
    @DisplayName("SNS Account 저장")
    void testSaveSnsAccount() {
        // given
        SnsAccount snsAccount = MockEntityFactoryService.getSnsAccount();

        // when
        sut.save(snsAccount);

        // then
        assertThat(repository.findWithUserEntity(snsAccount.getSnsAccountId().getSnsDomain(), snsAccount.getSnsAccountId().getSnsId()))
                .get().usingRecursiveComparison()
                .ignoringFields("id", "createdTime", "updatedTime")
                .isEqualTo(SnsAccountJpaEntity.create(snsAccount));
    }
}