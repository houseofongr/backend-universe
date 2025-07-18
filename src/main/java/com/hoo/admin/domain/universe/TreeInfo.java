package com.hoo.admin.domain.universe;

import com.hoo.admin.domain.universe.space.Space;
import com.hoo.admin.domain.universe.piece.Piece;
import lombok.Getter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@Getter
public class TreeInfo {
    private final Integer depth;
    private final TreeInfo parent;
    private final List<TreeInfo> children;
    private final UniverseTreeComponent universeTreeComponent;

    private TreeInfo(Integer depth, TreeInfo parent, List<TreeInfo> children, UniverseTreeComponent universeTreeComponent) {
        this.depth = depth;
        this.parent = parent;
        this.children = children;
        this.universeTreeComponent = universeTreeComponent;
    }

    public static TreeInfo create(TraversalComponents components) {
        return create(components.getUniverse(), components.getSpaces(), components.getPieces());
    }

    public static TreeInfo create(Universe universe, List<Space> spaces, List<Piece> pieces) {
        TreeInfo root = new TreeInfo(0, null, new ArrayList<>(), universe);

        List<Space> childSpaces = new ArrayList<>();
        List<Piece> childPieces = new ArrayList<>();

        for (Space space : spaces) {
            if (space.isRoot()) root.addChild(space);
            else childSpaces.add(space);
        }

        for (Piece piece : pieces) {
            if (piece.isRoot()) root.addChild(piece);
            else childPieces.add(piece);
        }

        for (TreeInfo child : root.children) {
            makeChildTree(child, childSpaces, childPieces);
        }

        return root;
    }

    private static void makeChildTree(TreeInfo tree, List<Space> spaces, List<Piece> pieces) {
        if (tree.getUniverseTreeComponent() instanceof Piece) return;

        List<Space> childSpaces = new ArrayList<>();
        List<Piece> childPieces = new ArrayList<>();

        for (Space space : spaces) {
            if (space.getBasicInfo().getParentSpaceId().equals(tree.getUniverseTreeComponent().getId()))
                tree.addChild(space);
            else childSpaces.add(space);
        }

        for (Piece piece : pieces) {
            if (piece.getBasicInfo().getParentSpaceId().equals(tree.getUniverseTreeComponent().getId()))
                tree.addChild(piece);
            else childPieces.add(piece);
        }

        for (TreeInfo child : tree.children) {
            makeChildTree(child, childSpaces, childPieces);
        }
    }

    public void addChild(UniverseTreeComponent universeTreeComponent) {
        this.children.add(createChild(this, universeTreeComponent));
    }

    private TreeInfo createChild(TreeInfo parent, UniverseTreeComponent universeTreeComponent) {
        TreeInfo treeInfo = new TreeInfo(parent.getDepth() + 1, parent, new ArrayList<>(), universeTreeComponent);
        universeTreeComponent.setTreeInfo(treeInfo);
        return treeInfo;
    }

    public UniverseTreeComponent getComponent(Class<? extends UniverseTreeComponent> clazz, Long id) {
        Deque<TreeInfo> queue = new ArrayDeque<>();
        queue.offer(this);

        while (!queue.isEmpty()) {
            TreeInfo treeInfo = queue.poll();
            UniverseTreeComponent component = treeInfo.getUniverseTreeComponent();

            if (clazz.isInstance(component) && treeInfo.getUniverseTreeComponent().getId().equals(id))
                return component;

            for (TreeInfo child : treeInfo.children) {
                queue.offer(child);
            }
        }

        return null;
    }

    public TraversalComponents getAllComponents() {
        Deque<TreeInfo> queue = new ArrayDeque<>();
        queue.offer(this);

        Universe universe = null;
        List<Space> spaces = new ArrayList<>();
        List<Piece> pieces = new ArrayList<>();

        while (!queue.isEmpty()) {
            TreeInfo treeInfo = queue.poll();
            UniverseTreeComponent component = treeInfo.getUniverseTreeComponent();

            switch (component) {
                case Universe universeComponent -> universe = universeComponent;
                case Space space -> spaces.add(space);
                case Piece piece -> pieces.add(piece);
                default -> throw new IllegalArgumentException("Unexpected type: " + component.getClass());
            }

            for (TreeInfo child : treeInfo.children) {
                queue.offer(child);
            }
        }

        return new TraversalComponents(universe, spaces, pieces);
    }
}
