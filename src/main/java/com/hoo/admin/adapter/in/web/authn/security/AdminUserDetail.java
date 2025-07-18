package com.hoo.admin.adapter.in.web.authn.security;

import com.hoo.common.adapter.in.web.GrantedAuthorityAdapter;
import com.hoo.common.adapter.out.persistence.entity.AdminJpaEntity;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AdminUserDetail implements UserDetails, CredentialsContainer {

    private final String username;
    private String password;
    private final List<GrantedAuthorityAdapter> authorities;

    public AdminUserDetail(AdminJpaEntity adminJpaEntity) {
        this.username = adminJpaEntity.getUsername();
        this.password = adminJpaEntity.getPassword();
        this.authorities = adminJpaEntity.getRole().getAuthorities()
                .stream().map(GrantedAuthorityAdapter::new)
                .toList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }
}
