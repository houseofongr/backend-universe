package com.hoo.admin.application.port.out.space;

import java.util.List;

public interface DeleteSpacePort {
    void deleteAll(List<Long> ids);
}
