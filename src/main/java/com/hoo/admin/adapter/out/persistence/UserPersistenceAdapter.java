package com.hoo.admin.adapter.out.persistence;

import com.hoo.admin.application.port.out.user.*;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import com.hoo.admin.domain.user.BusinessUser;
import com.hoo.common.adapter.out.persistence.entity.BusinessUserJpaEntity;
import com.hoo.common.adapter.out.persistence.repository.*;
import com.hoo.aar.application.service.AarErrorCode;
import com.hoo.aar.application.service.AarException;
import com.hoo.admin.adapter.out.persistence.mapper.UserMapper;
import com.hoo.admin.application.port.in.user.*;
import com.hoo.admin.domain.user.DeletedUser;
import com.hoo.admin.domain.user.User;
import com.hoo.common.adapter.out.persistence.entity.DeletedUserJpaEntity;
import com.hoo.common.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.common.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.common.application.port.in.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements SaveUserPort, SaveDeletedUserPort, SearchUserPort, FindUserPort, UpdateUserPort, DeleteUserPort, FindBusinessUserPort, RegisterBusinessUserPort {

    private final UserJpaRepository userJpaRepository;
    private final SnsAccountJpaRepository snsAccountJpaRepository;
    private final DeletedUserJpaRepository deletedUserJpaRepository;
    private final BusinessUserJpaRepository businessUserJpaRepository;
    private final SoundSourceJpaRepository soundSourceJpaRepository;
    private final ItemJpaRepository itemJpaRepository;
    private final HomeJpaRepository homeJpaRepository;
    private final UserMapper userMapper;

    @Override
    public Long save(User user) {

        List<SnsAccountJpaEntity> snsAccountJpaEntities = user.getSnsAccounts().stream().map(snsAccount ->
                snsAccountJpaRepository.findWithUserEntity(snsAccount.getSnsAccountId().getSnsDomain(), snsAccount.getSnsAccountId().getSnsId())
                        .orElseThrow(() -> new AarException(AarErrorCode.SNS_ACCOUNT_NOT_FOUND))
        ).toList();

        UserJpaEntity entity = UserJpaEntity.create(user, snsAccountJpaEntities);

        userJpaRepository.save(entity);

        return entity.getId();
    }

    @Override
    public Long saveDeletedUser(DeletedUser deletedUser) {
        DeletedUserJpaEntity deletedUserJpaEntity = DeletedUserJpaEntity.create(deletedUser);
        deletedUserJpaRepository.save(deletedUserJpaEntity);
        return deletedUserJpaEntity.getId();
    }

    @Override
    public QueryUserInfoResult query(QueryUserInfoCommand command) {
        Page<QueryUserInfoResult.UserInfo> userInfosPages = userJpaRepository.searchAll(command).map(userMapper::mapToQueryResult);
        return new QueryUserInfoResult(userInfosPages.getContent(), Pagination.of(userInfosPages));
    }

    @Override
    public SearchUserResult search(SearchUserCommand command) {
        Page<SearchUserResult.UserInfo> userInfosPages = userJpaRepository.searchAll(command).map(userMapper::mapToSearchResult);
        return new SearchUserResult(userInfosPages.getContent(), Pagination.of(userInfosPages));
    }

    @Override
    public Optional<User> loadUser(Long id) {
        return userJpaRepository.findById(id).map(userMapper::mapToDomainEntity);
    }

    @Override
    public boolean exist(Long id) {
        return userJpaRepository.existsById(id);
    }

    @Override
    public void updateUser(User user) {
        UserJpaEntity userJpaEntity = userJpaRepository.findById(user.getUserInfo().getId()).orElseThrow();
        userJpaEntity.update(user);
    }

    @Override
    public void deleteUser(User user) {
        Long userId = user.getUserInfo().getId();

        snsAccountJpaRepository.deleteAll(snsAccountJpaRepository.findAllByUserId(userId));
        soundSourceJpaRepository.deleteAll(soundSourceJpaRepository.findAllByUserId(userId));
        itemJpaRepository.deleteAll(itemJpaRepository.findAllByUserId(userId));
        homeJpaRepository.deleteAll(homeJpaRepository.findAllByUserId(userId));

        userJpaRepository.deleteById(userId);
    }

    @Override
    public BusinessUser findBusinessUser(Long businessUserId) {
        BusinessUserJpaEntity businessUserJpaEntity = businessUserJpaRepository.findById(businessUserId)
                .orElseThrow(() -> new AdminException(AdminErrorCode.USER_NOT_FOUND));

        return userMapper.mapToBusinessUserEntity(businessUserJpaEntity);
    }

    @Override
    public User registerBusinessUser(Long businessUserId) {
        BusinessUserJpaEntity businessUserJpaEntity = businessUserJpaRepository.findById(businessUserId)
                .orElseThrow(() -> new AdminException(AdminErrorCode.USER_NOT_FOUND));

        BusinessUser businessUser = userMapper.mapToBusinessUserEntity(businessUserJpaEntity);
        User user = User.createBusinessUser(businessUser);

        UserJpaEntity userJpaEntity = UserJpaEntity.createBusinessUser(user);
        userJpaRepository.save(userJpaEntity);

        businessUserJpaEntity.approve(userJpaEntity);

        return userMapper.mapToDomainEntity(userJpaEntity);
    }
}
