package com.hoo.common.adapter.out.persistence.entity;

import com.hoo.admin.domain.house.room.Room;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "ROOM")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private Float x;

    @Column(nullable = false)
    private Float y;

    @Column(nullable = false)
    private Float z;

    @Column(nullable = false)
    private Float width;

    @Column(nullable = false)
    private Float height;

    @Column(nullable = false)
    private Long imageFileId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOUSE_ID")
    private HouseJpaEntity house;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private List<ItemJpaEntity> items;

    public static RoomJpaEntity create(Room room) {
        return new RoomJpaEntity(
                null,
                room.getRoomDetail().getName(),
                room.getAxis().getX(),
                room.getAxis().getY(),
                room.getAxis().getZ(),
                room.getArea().getWidth(),
                room.getArea().getHeight(),
                room.getImageFile().getFileId().getId(),
                null,
                null);
    }

    public void update(String name) {
        this.name = name;
    }

    public void setRelationship(HouseJpaEntity houseJpaEntity) {
        if (!houseJpaEntity.getRooms().contains(this)) houseJpaEntity.getRooms().add(this);
        this.house = houseJpaEntity;
    }
}
