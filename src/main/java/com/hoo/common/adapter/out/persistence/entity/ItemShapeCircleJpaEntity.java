package com.hoo.common.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ITEM_SHAPE_CIRCLE")
@DiscriminatorValue("CIRCLE")
@Getter
@NoArgsConstructor
public class ItemShapeCircleJpaEntity extends ItemShapeJpaEntity {

    @Column(nullable = false)
    private Float radius;

    public ItemShapeCircleJpaEntity(Long id, Float x, Float y, Float radius) {
        super(id, x, y, null);
        this.radius = radius;
    }
}
