package com.hoo.admin.application.port.out.universe;

import com.hoo.admin.application.port.in.universe.SearchUniverseCommand;
import com.hoo.admin.application.port.in.universe.SearchUniverseResult;
import com.hoo.admin.domain.universe.TraversalComponents;
import com.hoo.admin.domain.universe.Universe;

public interface FindUniversePort {
    Universe load(Long id);

    SearchUniverseResult search(SearchUniverseCommand command);

    SearchUniverseResult.UniverseDetailInfo find(Long id);

    TraversalComponents findTreeComponents(Long id);
}
