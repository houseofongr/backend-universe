package com.hoo.common.adapter.out.persistence.entity;

import com.hoo.admin.domain.item.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "ITEM")
@Getter
@NoArgsConstructor
public class ItemJpaEntity extends DateColumnBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOME_ID")
    private HomeJpaEntity home;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_ID")
    private RoomJpaEntity room;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ITEM_SHAPE_ID")
    private ItemShapeJpaEntity shape;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
    List<SoundSourceJpaEntity> soundSources;

    private ItemJpaEntity(Long id, String name, Shape shape) {
        this.id = id;
        this.name = name;
        this.shape = getShapeEntity(shape);
    }

    public static ItemJpaEntity create(Item item) {
        return new ItemJpaEntity(
                null,
                item.getItemDetail().getName(),
                item.getShape());
    }

    public void setRelationship(HomeJpaEntity homeJpaEntity, RoomJpaEntity roomJpaEntity) {
        this.home = homeJpaEntity;
        this.room = roomJpaEntity;

        if (!homeJpaEntity.getItems().contains(this))
            roomJpaEntity.getItems().add(this);

        if (!roomJpaEntity.getItems().contains(this))
            roomJpaEntity.getItems().add(this);
    }

    public void update(Item item) {
        this.name = item.getItemDetail().getName();
        this.shape = getShapeEntity(item.getShape());
    }

    private ItemShapeJpaEntity getShapeEntity(Shape shape) {
        return switch (shape) {
            case Rectangle rectangle -> this.shape = new ItemShapeRectangleJpaEntity(
                    null,
                    rectangle.getX(),
                    rectangle.getY(),
                    rectangle.getWidth(),
                    rectangle.getHeight(),
                    rectangle.getRotation()
            );
            case Circle circle -> this.shape = new ItemShapeCircleJpaEntity(
                    null,
                    circle.getX(),
                    circle.getY(),
                    circle.getRadius()
            );
            case Ellipse ellipse -> this.shape = new ItemShapeEllipseJpaEntity(
                    null,
                    ellipse.getX(),
                    ellipse.getY(),
                    ellipse.getRadiusX(),
                    ellipse.getRadiusY(),
                    ellipse.getRotation()
            );
            default -> throw new IllegalStateException("Unexpected value: " + shape);
        };
    }
}
