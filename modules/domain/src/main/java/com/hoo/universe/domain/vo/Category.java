package com.hoo.universe.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.UUID;

@Value
public class Category {

    @EqualsAndHashCode.Include
    UUID id;

    @EqualsAndHashCode.Exclude
    String eng;

    @EqualsAndHashCode.Exclude
    String kor;
}
