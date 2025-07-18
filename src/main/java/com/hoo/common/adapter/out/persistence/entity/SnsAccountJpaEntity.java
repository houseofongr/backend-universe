package com.hoo.common.adapter.out.persistence.entity;

import com.hoo.admin.domain.user.snsaccount.SnsAccount;
import com.hoo.admin.domain.user.snsaccount.SnsDomain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SNS_ACCOUNT")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SnsAccountJpaEntity extends DateColumnBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String realName;

    @Column(nullable = false, length = 255)
    private String nickname;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String snsId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SnsDomain snsDomain;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = true)
    @Setter
    private UserJpaEntity userEntity;

    public static SnsAccountJpaEntity create(SnsAccount snsAccount) {
        return new SnsAccountJpaEntity(
                null,
                snsAccount.getSnsAccountInfo().getRealName(),
                snsAccount.getSnsAccountInfo().getNickname(),
                snsAccount.getSnsAccountInfo().getEmail(),
                snsAccount.getSnsAccountId().getSnsId(),
                snsAccount.getSnsAccountId().getSnsDomain(),
                null
        );
    }
}
