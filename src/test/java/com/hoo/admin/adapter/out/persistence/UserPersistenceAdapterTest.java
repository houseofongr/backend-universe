package com.hoo.admin.adapter.out.persistence;

import com.hoo.admin.domain.user.BusinessUser;
import com.hoo.common.adapter.out.persistence.repository.BusinessUserJpaRepository;
import com.hoo.common.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.admin.adapter.out.persistence.mapper.SnsAccountMapper;
import com.hoo.admin.adapter.out.persistence.mapper.UserMapper;
import com.hoo.admin.application.port.in.user.SearchUserCommand;
import com.hoo.admin.application.port.in.user.SearchUserResult;
import com.hoo.admin.domain.user.DeletedUser;
import com.hoo.admin.domain.user.User;
import com.hoo.common.adapter.out.persistence.PersistenceAdapterTest;
import com.hoo.common.adapter.out.persistence.entity.*;
import com.hoo.common.adapter.out.persistence.repository.DeletedUserJpaRepository;
import com.hoo.common.application.service.MockEntityFactoryService;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.TemporalUnitWithinOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static com.hoo.admin.domain.user.snsaccount.SnsDomain.KAKAO;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Sql("UserPersistenceAdapterTest.sql")
@PersistenceAdapterTest
@Import({UserPersistenceAdapter.class, UserMapper.class, SnsAccountMapper.class})
class UserPersistenceAdapterTest {

    @Autowired
    UserPersistenceAdapter sut;

    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    DeletedUserJpaRepository deletedUserJpaRepository;

    @Autowired
    BusinessUserJpaRepository businessUserJpaRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("사용자 검색 기능 테스트")
    void testSearchUser() {
        // given
        SearchUserCommand command = new SearchUserCommand(PageRequest.of(0, 10), null, null, null, null);

        // when
        SearchUserResult result = sut.search(command);

        // then
        assertThat(result.users()).hasSize(8);
        assertThat(result.users()).anySatisfy(userInfo -> {
                    assertThat(userInfo.name()).isEqualTo("남상엽");
                    assertThat(userInfo.nickname()).isEqualTo("lea");
                    assertThat(userInfo.phoneNumber()).isEqualTo("01012345678");
                    assertThat(userInfo.registeredDate()).isInstanceOf(Long.class);
                    assertThat(userInfo.snsAccounts().getFirst().domain()).isEqualTo(KAKAO);
                    assertThat(userInfo.snsAccounts().getFirst().email()).isEqualTo("test@example.com");
                }
        );
    }

    @Test
    @DisplayName("사용자 검색 파라미터 테스트")
    void testSearchParameter() {
        // given
        SearchUserCommand nickLeaf = new SearchUserCommand(PageRequest.of(0, 10), "nickname", "leaf", null, null);
        SearchUserCommand name남 = new SearchUserCommand(PageRequest.of(0, 10), "name", "남", null, null);
        SearchUserCommand emailStone = new SearchUserCommand(PageRequest.of(0, 10), "email", "stone", null, null);
        SearchUserCommand phone3158 = new SearchUserCommand(PageRequest.of(0, 10), "phone_number", "3158", null, null);

        // when
        SearchUserResult nickLeafResult = sut.search(nickLeaf);
        SearchUserResult name남Result = sut.search(name남);
        SearchUserResult emailStoneResult = sut.search(emailStone);
        SearchUserResult phone3158Result = sut.search(phone3158);

        // then
        assertThat(nickLeafResult.users()).hasSize(5).allMatch(userInfo -> userInfo.nickname().contains("leaf"));
        assertThat(name남Result.users()).hasSize(6).allMatch(userInfo -> userInfo.name().contains("남"));
        assertThat(emailStoneResult.users()).hasSize(2).allMatch(userInfo -> userInfo.email().contains("stone"));
        assertThat(phone3158Result.users()).hasSize(3).allMatch(userInfo -> userInfo.phoneNumber().contains("3158"));
    }

    @Test
    @DisplayName("사용자 검색 정렬 테스트")
    void testOrder() {
        // given
        SearchUserCommand 이름_오름차순 = new SearchUserCommand(PageRequest.of(0, 10), null, null, "name", true);
        SearchUserCommand 닉네임_오름차순 = new SearchUserCommand(PageRequest.of(0, 10), null, null, "nickname", true);
        SearchUserCommand 등록일_오름차순 = new SearchUserCommand(PageRequest.of(0, 10), null, null, "registered_date", true);
        SearchUserCommand 이름_내림차순 = new SearchUserCommand(PageRequest.of(0, 10), null, null, "name", false);
        SearchUserCommand 닉네임_내림차순 = new SearchUserCommand(PageRequest.of(0, 10), null, null, "nickname", false);
        SearchUserCommand 등록일_내림차순 = new SearchUserCommand(PageRequest.of(0, 10), null, null, "registered_date", false);

        // when
        SearchUserResult 이름_오름차순_result = sut.search(이름_오름차순);
        SearchUserResult 닉네임_오름차순_result = sut.search(닉네임_오름차순);
        SearchUserResult 등록일_오름차순_result = sut.search(등록일_오름차순);
        SearchUserResult 이름_내림차순_result = sut.search(이름_내림차순);
        SearchUserResult 닉네임_내림차순_result = sut.search(닉네임_내림차순);
        SearchUserResult 등록일_내림차순_result = sut.search(등록일_내림차순);

        // then
        assertThat(이름_오름차순_result.users().getLast().name()).matches("엽상");
        assertThat(이름_내림차순_result.users().getFirst().name()).isEqualTo("엽상");
        assertThat(닉네임_오름차순_result.users().getLast().nickname()).isEqualTo("upstone");
        assertThat(닉네임_내림차순_result.users().getFirst().nickname()).isEqualTo("upstone");
        assertThat(등록일_오름차순_result.users().getFirst().nickname()).isEqualTo("leafstone");
        assertThat(등록일_내림차순_result.users().getFirst().nickname()).isEqualTo("lea");
    }

    @Test
    @DisplayName("사용자 저장")
    void testSaveUser() {
        // given
        User user = MockEntityFactoryService.getUser();

        // when
        Long savedId = sut.save(user);
        Optional<UserJpaEntity> optional = userJpaRepository.findById(savedId);

        // then
        Assertions.assertThat(optional).isNotEmpty();
        Assertions.assertThat(optional.get().getNickname()).isEqualTo(user.getUserInfo().getNickname());
        Assertions.assertThat(optional.get().getRealName()).isEqualTo(user.getUserInfo().getRealName());
        Assertions.assertThat(optional.get().getPhoneNumber()).isNull();
        Assertions.assertThat(optional.get().getPersonalInformationAgreement()).isEqualTo(user.getAgreement().getPersonalInformationAgreement());
        Assertions.assertThat(optional.get().getTermsOfUseAgreement()).isEqualTo(user.getAgreement().getTermsOfUseAgreement());
        Assertions.assertThat(optional.get().getCreatedTime()).isCloseTo(ZonedDateTime.now(), new TemporalUnitWithinOffset(1L, ChronoUnit.SECONDS));
        Assertions.assertThat(optional.get().getUpdatedTime()).isCloseTo(ZonedDateTime.now(), new TemporalUnitWithinOffset(1L, ChronoUnit.SECONDS));
    }

    @Test
    @DisplayName("사용자 정보 업데이트 테스트")
    void testUserInfoUpdate() {
        // given
        User user = sut.loadUser(10L).get();
        user.updateNickname("leaf2");

        // when
        sut.updateUser(user);

        // then
        Optional<UserJpaEntity> optional = userJpaRepository.findById(user.getUserInfo().getId());
        assertThat(optional).isPresent();
        assertThat(optional.get().getNickname()).isEqualTo("leaf2");
    }

    @Test
    @DisplayName("회원탈퇴자 정보 저장 테스트")
    void testSaveDeletedUser() {
        // given
        User user = sut.loadUser(10L).get();
        DeletedUser deletedUser = DeletedUser.create(1L, user, true, true);

        // when
        Long deletedUserId = sut.saveDeletedUser(deletedUser);

        // then
        assertThat(deletedUserId).isNotNull();

        DeletedUserJpaEntity deletedUserJpaEntity = deletedUserJpaRepository.findById(deletedUserId).get();
        assertThat(deletedUserJpaEntity.getNickname()).isEqualTo(user.getUserInfo().getMaskedNickname());
        assertThat(deletedUserJpaEntity.getTermsOfDeletionAgreement()).isTrue();
        assertThat(deletedUserJpaEntity.getPersonalInformationDeletionAgreement()).isTrue();
    }

    @Test
    @DisplayName("회원정보 삭제 테스트")
    void testDeleteUserInfo() {
        // given
        User user = sut.loadUser(10L).get();

        // when
        sut.deleteUser(user);

        // then
        assertThat(userJpaRepository.findById(user.getUserInfo().getId())).isEmpty();
        assertThat(entityManager.find(SnsAccountJpaEntity.class, 1L)).isNull();
        assertThat(entityManager.find(SoundSourceJpaEntity.class, 1L)).isNull();
        assertThat(entityManager.find(SoundSourceJpaEntity.class, 2L)).isNull();
        assertThat(entityManager.find(SoundSourceJpaEntity.class, 3L)).isNull();
        assertThat(entityManager.find(ItemJpaEntity.class, 1L)).isNull();
        assertThat(entityManager.find(ItemJpaEntity.class, 2L)).isNull();
        assertThat(entityManager.find(ItemJpaEntity.class, 3L)).isNull();
        assertThat(entityManager.find(ItemShapeRectangleJpaEntity.class, 1L)).isNull();
        assertThat(entityManager.find(ItemShapeCircleJpaEntity.class, 2L)).isNull();
        assertThat(entityManager.find(ItemShapeEllipseJpaEntity.class, 3L)).isNull();
        assertThat(entityManager.find(HomeJpaEntity.class, 1L)).isNull();
        assertThat(entityManager.find(HomeJpaEntity.class, 2L)).isNull();
    }

    @Test
    @DisplayName("비즈니스 사용자 조회 테스트")
    void testFindBusinessUser() {
        // given
        Long businessUserId = 1L;

        // when
        BusinessUser businessUser = sut.findBusinessUser(businessUserId);

        // then
        assertThat(businessUser.getBusinessUserId()).isEqualTo(businessUserId);
        assertThat(businessUser.getUserInfo().getId()).isNull();
        assertThat(businessUser.getUserInfo().getId()).isNull();
        assertThat(businessUser.getUserInfo().getRealName()).isNull();
        assertThat(businessUser.getUserInfo().getEmail()).isEqualTo("test@example.com");
        assertThat(businessUser.getUserInfo().getNickname()).isEqualTo("temp_user123");
        assertThat(businessUser.getAgreement().getTermsOfUseAgreement()).isTrue();
        assertThat(businessUser.getAgreement().getPersonalInformationAgreement()).isTrue();
        assertThat(businessUser.getBaseTime().getCreatedTime()).isEqualTo(ZonedDateTime.of(2025, 6, 27, 20, 15, 0, 0, ZoneOffset.UTC));
        assertThat(businessUser.getBaseTime().getUpdatedTime()).isEqualTo(ZonedDateTime.of(2025, 6, 27, 20, 15, 0, 0, ZoneOffset.UTC));
    }

    @Test
    @DisplayName("비즈니스 사용자 등록 테스트")
    void testRegisterBusinessUser() {
        // given
        Long businessUserId = 1L;

        // when
        User user = sut.registerBusinessUser(businessUserId);
        BusinessUserJpaEntity businessUserJpaEntity = businessUserJpaRepository.findById(businessUserId).orElseThrow();

        // then
        assertThat(user.getUserInfo().getId()).isEqualTo(user.getUserInfo().getId());
        assertThat(businessUserJpaEntity.getUserJpaEntity().getId()).isEqualTo(user.getUserInfo().getId());
        assertThat(businessUserJpaEntity.getUserJpaEntity().getUserType()).isEqualTo(UserJpaEntity.Type.BUSINESS);
        assertThat(businessUserJpaEntity.getStatus()).isEqualTo(BusinessUserJpaEntity.Status.APPROVED);
        assertThat(businessUserJpaEntity.getApprovedAt()).isCloseTo(ZonedDateTime.now(), new TemporalUnitWithinOffset(1, ChronoUnit.SECONDS));
    }
}