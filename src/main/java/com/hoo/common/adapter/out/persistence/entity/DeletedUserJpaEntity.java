package com.hoo.common.adapter.out.persistence.entity;

import com.hoo.admin.domain.user.DeletedUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "DELETED_USER")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeletedUserJpaEntity extends DateColumnBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String maskedRealName;

    @Column(nullable = false, length = 255)
    private String nickname;

    @Column(length = 255)
    private String maskedEmail;

    @Column(length = 20)
    private String maskedPhoneNumber;

    @Column(nullable = false)
    @ColumnDefault("1")
    private Boolean termsOfDeletionAgreement;

    @Column(nullable = false)
    @ColumnDefault("1")
    private Boolean personalInformationDeletionAgreement;

    public static DeletedUserJpaEntity create(DeletedUser deletedUser) {
        return new DeletedUserJpaEntity(
                null,
                deletedUser.getUserInfo().getMaskedRealName(),
                deletedUser.getUserInfo().getMaskedNickname(),
                deletedUser.getUserInfo().getMaskedEmail(),
                null,
                deletedUser.getAgreement().getTermsOfDeletionAgreement(),
                deletedUser.getAgreement().getPersonalInformationDeletionAgreement()
        );
    }
}
