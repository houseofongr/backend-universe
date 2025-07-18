package com.hoo.admin.domain.universe;

import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class UniverseTreeComponent {

    private final Long id;

    @Setter
    private TreeInfo treeInfo;

    protected UniverseTreeComponent(Long id, TreeInfo treeInfo) {
        this.id = id;
        this.treeInfo = treeInfo;
    }

}
