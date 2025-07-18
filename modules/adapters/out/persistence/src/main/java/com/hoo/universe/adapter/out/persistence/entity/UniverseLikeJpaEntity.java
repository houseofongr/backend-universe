package com.hoo.universe.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "UNIVERSE_LIKE")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UniverseLikeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UNIVERSE_ID")
    private UniverseJpaEntity universe;

    @Column(name = "USER_ID")
    private Long userID;

}
