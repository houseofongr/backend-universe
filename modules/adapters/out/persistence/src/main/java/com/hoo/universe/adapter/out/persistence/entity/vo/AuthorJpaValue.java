package com.hoo.universe.adapter.out.persistence.entity.vo;

import com.hoo.universe.domain.vo.Owner;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class AuthorJpaValue {

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID ownerId;

    @Column(nullable = false)
    private String ownerNickname;


    public static AuthorJpaValue from(Owner owner) {
        return new AuthorJpaValue(
                owner.getId(),
                owner.getNickname()
        );
    }
}
