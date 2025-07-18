package com.hoo.admin.domain.universe;

import com.hoo.admin.domain.universe.space.Space;
import com.hoo.admin.domain.universe.piece.Piece;
import lombok.Getter;

import java.util.List;

@Getter
public class TraversalComponents {
    private final Universe universe;
    private final List<Space> spaces;
    private final List<Piece> pieces;

    public TraversalComponents(Universe universe, List<Space> spaces, List<Piece> pieces) {
        this.universe = universe;
        this.spaces = spaces;
        this.pieces = pieces;
    }
}
