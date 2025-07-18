package com.hoo.admin.application.port.in.space;

import com.hoo.admin.domain.universe.TreeInfo;

public interface DeleteSpaceUseCase {
    DeleteSpaceResult delete(Long spaceId);
    DeleteSpaceResult deleteSubtree(Long spaceId, TreeInfo subtree);
}
