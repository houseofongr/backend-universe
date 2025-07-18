package com.hoo.common.adapter.out.persistence.entity;

import com.hoo.common.domain.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ADMIN")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = true, length = 255)
    private String nickname;

    @Column(nullable = true, length = 255)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SNS_ACCOUNT_ID")
    private SnsAccountJpaEntity snsAccount;

}
