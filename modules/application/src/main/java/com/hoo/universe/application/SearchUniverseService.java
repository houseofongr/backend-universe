package com.hoo.universe.application;

import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.universe.api.in.dto.SearchUniverseCommand;
import com.hoo.universe.api.out.dto.UniverseListQueryInfo;
import com.hoo.universe.api.in.dto.UniverseListInfo;
import com.hoo.universe.api.in.SearchUniverseUseCase;
import com.hoo.universe.api.out.QueryUniversePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchUniverseService implements SearchUniverseUseCase {

    private final QueryUniversePort queryUniversePort;

    @Override
    public PageQueryResult<UniverseListInfo> searchUniverse(SearchUniverseCommand command) {
        return mapToAPIResult(queryUniversePort.searchUniverses(command));
    }

    private PageQueryResult<UniverseListInfo> mapToAPIResult(PageQueryResult<UniverseListQueryInfo> universeListQueryInfoPageQueryResult) {
        return null;
    }
}
