package com.hoo.admin.adapter.out.persistence;

import com.hoo.common.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.admin.adapter.out.persistence.mapper.HomeMapper;
import com.hoo.admin.application.port.out.home.DeleteHomePort;
import com.hoo.admin.application.port.out.home.FindHomePort;
import com.hoo.admin.application.port.out.home.SaveHomePort;
import com.hoo.admin.application.port.out.home.UpdateHomePort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import com.hoo.admin.domain.home.Home;
import com.hoo.common.adapter.out.persistence.entity.HomeJpaEntity;
import com.hoo.common.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.common.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.common.adapter.out.persistence.repository.HomeJpaRepository;
import com.hoo.common.adapter.out.persistence.repository.HouseJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HomePersistenceAdapter implements SaveHomePort, FindHomePort, UpdateHomePort, DeleteHomePort {

    private final HouseJpaRepository houseJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final HomeJpaRepository homeJpaRepository;
    private final HomeMapper homeMapper;

    @Override
    public Long save(Home home) {
        UserJpaEntity userJpaEntity = userJpaRepository.findById(home.getOwnerId()).orElseThrow();
        HouseJpaEntity houseJpaEntity = houseJpaRepository.findById(home.getHouseId().getId()).orElseThrow();

        if (homeJpaRepository.existsByHouseIdAndUserId(home.getHouseId().getId(), home.getOwnerId()))
            throw new AdminException(AdminErrorCode.ALREADY_CREATED_HOME);

        HomeJpaEntity homeJpaEntity = HomeJpaEntity.create(home);
        homeJpaEntity.setRelationship(userJpaEntity, houseJpaEntity);

        homeJpaRepository.save(homeJpaEntity);

        return homeJpaEntity.getId();
    }

    @Override
    public boolean exist(Long homeId) {
        return homeJpaRepository.existsById(homeId);
    }

    @Override
    public boolean existByHouseId(Long houseId) {
        return homeJpaRepository.existsByHouseId(houseId);
    }

    @Override
    public Optional<Home> loadHome(Long id) {
        return homeJpaRepository.findById(id)
                .map(homeMapper::mapToDomainEntity);
    }

    @Override
    public List<Home> loadHomes(Long userId) {
        return homeJpaRepository.findAllByUserId(userId)
                .stream().map(homeMapper::mapToDomainEntity).toList();
    }

    @Override
    public void updateHomeName(Home home) {
        HomeJpaEntity homeJpaEntity = homeJpaRepository.findById(home.getHomeId().getId()).orElseThrow();
        homeJpaEntity.updateName(home.getHomeDetail().getName());
    }

    @Override
    public void updateMainHome(Long userId, Long homeId) {
        homeJpaRepository.findByUserIdAndIsMain(userId, true).ifPresent(homeJpaEntity -> homeJpaEntity.isMainHome(false));
        homeJpaRepository.findById(homeId).orElseThrow().isMainHome(true);
    }

    @Override
    public void deleteHome(Long id) {
        if (!homeJpaRepository.existsById(id))
            throw new AdminException(AdminErrorCode.HOME_NOT_FOUND);

        homeJpaRepository.deleteById(id);
    }
}
