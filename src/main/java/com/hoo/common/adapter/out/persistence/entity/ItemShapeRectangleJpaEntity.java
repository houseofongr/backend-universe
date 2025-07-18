package com.hoo.common.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ITEM_SHAPE_RECTANGLE")
@DiscriminatorValue("RECTANGLE")
@Getter
@NoArgsConstructor
public class ItemShapeRectangleJpaEntity extends ItemShapeJpaEntity {

    @Column(nullable = false)
    private Float width;

    @Column(nullable = false)
    private Float height;

    @Column(nullable = false)
    private Float rotation;

    public ItemShapeRectangleJpaEntity(Long id, Float x, Float y, Float width, Float height, Float rotation) {
        super(id, x, y, null);
        this.width = width;
        this.height = height;
        this.rotation = rotation;
    }
}
