package com.hoo.universe.api.in;

import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.universe.api.in.dto.SearchUniverseCommand;
import com.hoo.universe.api.in.dto.UniverseListInfo;

public interface SearchUniverseUseCase {
    PageQueryResult<UniverseListInfo> searchUniverse(SearchUniverseCommand command);
}
