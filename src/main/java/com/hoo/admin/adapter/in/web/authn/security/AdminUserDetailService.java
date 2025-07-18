package com.hoo.admin.adapter.in.web.authn.security;

import com.hoo.common.adapter.out.persistence.entity.AdminJpaEntity;
import com.hoo.common.adapter.out.persistence.repository.AdminJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminUserDetailService implements UserDetailsService {

    private final AdminJpaRepository adminJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminJpaEntity admin = adminJpaRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found Username : " + username));

        return new AdminUserDetail(admin);
    }
}
