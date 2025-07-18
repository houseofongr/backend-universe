package com.hoo.common.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Table(name = "BUSINESS_USER")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessUserJpaEntity extends DateColumnBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private Boolean termsOfUseAgreement;

    @Column(nullable = false)
    private Boolean personalInformationAgreement;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        WAITING, APPROVED, REJECTED
    }

    @Column
    private ZonedDateTime approvedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UserJpaEntity userJpaEntity;

    public static BusinessUserJpaEntity create(String email, String encryptedPassword, String nickname, Boolean termsOfUseAgreement, Boolean personalInformationAgreement) {
        return new BusinessUserJpaEntity(null, email, encryptedPassword, nickname, termsOfUseAgreement, personalInformationAgreement, Status.WAITING, null, null);
    }

    public void approve(UserJpaEntity userJpaEntity) {
        this.status = Status.APPROVED;
        this.approvedAt = ZonedDateTime.now();
        this.userJpaEntity = userJpaEntity;
    }

}
