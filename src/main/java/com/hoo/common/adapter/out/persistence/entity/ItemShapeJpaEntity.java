package com.hoo.common.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DiscriminatorOptions;

@Entity
@Table(name = "ITEM_SHAPE")
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorOptions(force = true)
@DiscriminatorColumn
@NoArgsConstructor
@AllArgsConstructor
public abstract class ItemShapeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Float x;

    @Column(nullable = false)
    private Float y;

    @OneToOne(mappedBy = "shape", fetch = FetchType.EAGER)
    private ItemJpaEntity item;

}
