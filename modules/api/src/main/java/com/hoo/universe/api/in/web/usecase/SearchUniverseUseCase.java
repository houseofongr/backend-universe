package com.hoo.universe.api.in.web.usecase;

import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.universe.api.in.web.dto.command.SearchUniverseCommand;
import com.hoo.universe.api.in.web.dto.result.UniverseListInfo;

public interface SearchUniverseUseCase {
    PageQueryResult<UniverseListInfo> searchUniverse(SearchUniverseCommand command);
}
