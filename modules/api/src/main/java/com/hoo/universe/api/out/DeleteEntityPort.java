package com.hoo.universe.api.out;

import com.hoo.universe.domain.event.PieceDeleteEvent;
import com.hoo.universe.domain.event.SoundDeleteEvent;
import com.hoo.universe.domain.event.SpaceDeleteEvent;
import com.hoo.universe.domain.event.UniverseDeleteEvent;

public interface DeleteEntityPort {
    void deleteUniverse(UniverseDeleteEvent event);

    void deleteSpace(SpaceDeleteEvent event);

    void deletePiece(PieceDeleteEvent event);

    void deleteSound(SoundDeleteEvent event);
}
