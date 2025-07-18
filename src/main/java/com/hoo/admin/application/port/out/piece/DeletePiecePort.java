package com.hoo.admin.application.port.out.piece;

import java.util.List;

public interface DeletePiecePort {
    void deleteAll(List<Long> ids);
    void delete(Long id);
}
