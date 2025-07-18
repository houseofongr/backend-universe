package com.hoo.common.adapter.out.persistence.entity;

import com.hoo.admin.domain.house.House;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "HOUSE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class HouseJpaEntity extends DateColumnBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 255)
    private String author;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(nullable = false)
    private Float width;

    @Column(nullable = false)
    private Float height;

    @Column(nullable = false)
    private Long basicImageFileId;

    @Column(nullable = false)
    private Long borderImageFileId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "house")
    private List<RoomJpaEntity> rooms;

    public static HouseJpaEntity create(House house) {
        return new HouseJpaEntity(
                null,
                house.getHouseDetail().getTitle(),
                house.getHouseDetail().getAuthor(),
                house.getHouseDetail().getDescription(),
                house.getArea().getWidth(),
                house.getArea().getHeight(),
                house.getBasicImageFile().getFileId().getId(),
                house.getBorderImageFile().getFileId().getId(),
                new ArrayList<>()
        );
    }

    public void update(House house) {
        this.title = house.getHouseDetail().getTitle();
        this.author = house.getHouseDetail().getAuthor();
        this.description = house.getHouseDetail().getDescription();
    }

}
