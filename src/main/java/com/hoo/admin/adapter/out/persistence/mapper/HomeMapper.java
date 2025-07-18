package com.hoo.admin.adapter.out.persistence.mapper;

import com.hoo.admin.domain.home.Home;
import com.hoo.common.adapter.out.persistence.entity.HomeJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class HomeMapper {

    public Home mapToDomainEntity(HomeJpaEntity homeJpaEntity) {
        return Home.load(homeJpaEntity.getId(), homeJpaEntity.getHouse().getId(), homeJpaEntity.getUser().getId(), homeJpaEntity.getName(), homeJpaEntity.getCreatedTime(), homeJpaEntity.getUpdatedTime());
    }

}
