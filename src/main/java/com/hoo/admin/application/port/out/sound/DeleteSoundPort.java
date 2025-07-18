package com.hoo.admin.application.port.out.sound;

import java.util.List;

public interface DeleteSoundPort {
    void delete(Long id);
    void deleteAll(List<Long> ids);
}
