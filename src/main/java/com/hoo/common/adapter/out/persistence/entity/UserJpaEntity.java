package com.hoo.common.adapter.out.persistence.entity;

import com.hoo.admin.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "AAR_USER")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserJpaEntity extends DateColumnBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String realName;

    @Column(unique = true)
    private String nickname;

    @Column
    private String email;

    @Column(length = 20)
    private String phoneNumber;

    @Column(nullable = false)
    private Boolean termsOfUseAgreement;

    @Column(nullable = false)
    private Boolean personalInformationAgreement;

    @Column
    @Enumerated(EnumType.STRING)
    private Type userType;

    public enum Type {
        BUSINESS, CONSUMER;

    }
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userEntity")
    private List<SnsAccountJpaEntity> snsAccountEntities;

    public static UserJpaEntity create(User user, List<SnsAccountJpaEntity> snsAccountJpaEntities) {

        UserJpaEntity userJpaEntity = new UserJpaEntity(null,
                user.getUserInfo().getRealName(),
                user.getUserInfo().getNickname(),
                user.getUserInfo().getEmail(),
                null,
                user.getAgreement().getTermsOfUseAgreement(),
                user.getAgreement().getPersonalInformationAgreement(),
                Type.CONSUMER,
                snsAccountJpaEntities);

        snsAccountJpaEntities.forEach(snsAccountJpaEntity -> snsAccountJpaEntity.setUserEntity(userJpaEntity));

        return userJpaEntity;
    }

    public static UserJpaEntity createBusinessUser(User user) {
        return new UserJpaEntity(null,
                user.getUserInfo().getRealName(),
                user.getUserInfo().getNickname(),
                user.getUserInfo().getEmail(),
                null,
                user.getAgreement().getTermsOfUseAgreement(),
                user.getAgreement().getPersonalInformationAgreement(),
                Type.BUSINESS,
                List.of()
        );
    }

    public void update(User user) {
        nickname = user.getUserInfo().getNickname();
    }
}
