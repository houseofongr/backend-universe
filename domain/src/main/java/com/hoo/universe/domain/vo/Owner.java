package com.hoo.universe.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.UUID;

@Value
public class Owner {

    @EqualsAndHashCode.Include
    UUID id;

    @EqualsAndHashCode.Exclude
    String nickname;
}
