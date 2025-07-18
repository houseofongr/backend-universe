package com.hoo.admin.application.port.in.home;

import com.hoo.common.application.port.in.MessageDto;

public interface DeleteHomeUseCase {
    MessageDto deleteHome(Long id);
}
