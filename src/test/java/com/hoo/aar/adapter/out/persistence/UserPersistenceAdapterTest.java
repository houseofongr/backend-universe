package com.hoo.aar.adapter.out.persistence;

import com.hoo.aar.adapter.out.persistence.mapper.SnsAccountMapper;
import com.hoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aar.application.port.in.user.SearchMyInfoResult;
import com.hoo.aar.application.port.out.persistence.user.BusinessUserInfo;
import com.hoo.common.adapter.out.persistence.PersistenceAdapterTest;
import com.hoo.common.adapter.out.persistence.entity.BusinessUserJpaEntity;
import com.hoo.common.adapter.out.persistence.repository.BusinessUserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@PersistenceAdapterTest
@Import({UserPersistenceAdapter.class, UserMapper.class, SnsAccountMapper.class})
@Sql("UserPersistenceAdapterTest.sql")
class UserPersistenceAdapterTest {

    @Autowired
    UserPersistenceAdapter sut;

    @Autowired
    BusinessUserJpaRepository businessUserJpaRepository;

    @Test
    @DisplayName("본인정보 조회")
    void testQueryMyInfo() {
        // given
        Long userId = 10L;

        // when
        SearchMyInfoResult result = sut.queryMyInfo(userId);

        // then
        assertThat(result.nickname()).isEqualTo("leaf");
        assertThat(result.email()).isEqualTo("test@example.com");
        assertThat(result.registeredDate()).matches("^(January|February|March|April|May|June|July|August|September|October|November|December)\\.\\d{2}\\.\\s\\d{4}$");
        assertThat(result.termsOfUseAgreement()).isTrue();
        assertThat(result.personalInformationAgreement()).isTrue();
        assertThat(result.myHomeCount()).isEqualTo(2);
        assertThat(result.mySoundSourceCount()).isEqualTo(3);
        assertThat(result.snsAccountInfos()).hasSize(1)
                .anySatisfy(soundSource -> {
                    assertThat(soundSource.domain()).isEqualTo("KAKAO");
                    assertThat(soundSource.email()).isEqualTo("test@example.com");
                });
    }

    @Test
    @DisplayName("닉네임 조회")
    void testExistNickname() {
        // given
        String nickname1 = "leaf";
        String nickname2 = "notExistNickName";

        // when
        boolean result1 = sut.existUserByNickname(nickname1);
        boolean result2 = sut.existUserByNickname(nickname2);

        // then
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @Test
    @DisplayName("비즈니스 임시회원 생성")
    void testSaveTempBusinessUser() {
        // given
        String email = "test@example.com";
        String password = "{bcrypt}$2a$10$E4pJtJxQtjHHH11j8/tNTOJljXDFJc3NWGlbCt00SZQB8Nn7DiKF.";
        String nickname = "temp_user_123";

        // when
        Long savedId = sut.save(email, password, nickname, true, true);
        BusinessUserJpaEntity businessUserJpaEntity = businessUserJpaRepository.findById(savedId).orElseThrow();

        // then
        assertThat(businessUserJpaEntity.getEmail()).isEqualTo(email);
        assertThat(businessUserJpaEntity.getPassword()).isEqualTo(password);
        assertThat(businessUserJpaEntity.getNickname()).isEqualTo(nickname);
        assertThat(businessUserJpaEntity.getStatus()).isEqualTo(BusinessUserJpaEntity.Status.WAITING);
        assertThat(businessUserJpaEntity.getApprovedAt()).isNull();
    }

    @Test
    @DisplayName("비즈니스 사용자 검색")
    void testFindBusinessUser() {
        // given
        String email = "test2@example.com";

        // when
        BusinessUserInfo businessUserInfo = sut.findBusinessUserInfo(email);

        // then
        assertThat(businessUserInfo.email()).isEqualTo(email);
        assertThat(businessUserInfo.nickname()).isEqualTo("temp_user123");
        assertThat(businessUserInfo.userId()).isEqualTo(11L);
    }
}