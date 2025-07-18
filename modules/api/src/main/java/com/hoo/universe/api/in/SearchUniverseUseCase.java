package com.hoo.universe.api.in;

import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.universe.api.dto.command.SearchUniverseCommand;
import com.hoo.universe.api.dto.result.UniverseListInfo;

public interface SearchUniverseUseCase {
    PageQueryResult<UniverseListInfo> searchUniverse(SearchUniverseCommand command);
}
