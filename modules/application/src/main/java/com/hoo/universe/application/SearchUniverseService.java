package com.hoo.universe.application;

import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.universe.api.in.web.dto.command.SearchUniverseCommand;
import com.hoo.universe.api.in.web.dto.query.UniverseListQueryInfo;
import com.hoo.universe.api.in.web.dto.result.UniverseListInfo;
import com.hoo.universe.api.in.web.usecase.SearchUniverseUseCase;
import com.hoo.universe.api.out.persistence.QueryUniversePort;
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
