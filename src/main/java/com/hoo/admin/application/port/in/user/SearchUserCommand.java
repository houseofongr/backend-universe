package com.hoo.admin.application.port.in.user;

import org.springframework.data.domain.Pageable;

public record SearchUserCommand(
        Pageable pageable,
        String searchType,
        String keyword,
        String sortType,
        Boolean isAsc) {

}
