package com.hoo.common.adapter.out.persistence.entity;

import com.hoo.admin.domain.home.Home;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "HOME")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HomeJpaEntity extends DateColumnBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column
    private Boolean isMain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOUSE_ID", nullable = false)
    private HouseJpaEntity house;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserJpaEntity user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "home")
    private List<ItemJpaEntity> items;

    public static HomeJpaEntity create(Home home) {
        return new HomeJpaEntity(
                null,
                home.getHomeDetail().getName(),
                false,
                null,
                null,
                null);
    }

    public void setRelationship(UserJpaEntity userJpaEntity, HouseJpaEntity houseJpaEntity) {
        this.user = userJpaEntity;
        this.house = houseJpaEntity;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void isMainHome(boolean isMain) {
        this.isMain = isMain;
    }
}
