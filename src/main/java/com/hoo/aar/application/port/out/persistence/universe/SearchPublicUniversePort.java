package com.hoo.aar.application.port.out.persistence.universe;

import com.hoo.aar.application.port.in.universe.SearchPublicUniverseCommand;
import com.hoo.aar.application.port.in.universe.SearchPublicUniverseResult;

public interface SearchPublicUniversePort {
    SearchPublicUniverseResult searchPublicUniverse(SearchPublicUniverseCommand command);
}
