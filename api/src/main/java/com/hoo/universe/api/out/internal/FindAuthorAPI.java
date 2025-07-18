package com.hoo.universe.api.out.internal;

import com.hoo.universe.domain.vo.Author;

import java.util.UUID;

public interface FindAuthorAPI {
    Author findAuthor(UUID authorID);
}
