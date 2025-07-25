package com.hoo.universe.api.out;

import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Sound;
import com.hoo.universe.domain.Space;
import com.hoo.universe.domain.Universe;

public interface SaveEntityPort {
    void saveUniverse(Universe universe);

    void saveSpace(Space space);

    void savePiece(Piece piece);

    void saveSound(Sound sound);
}
