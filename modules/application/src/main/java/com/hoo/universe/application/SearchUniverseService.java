package com.hoo.universe.application;

import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.universe.api.dto.command.SearchUniverseCommand;
import com.hoo.universe.api.dto.result.UniverseListInfo;
import com.hoo.universe.api.in.SearchUniverseUseCase;
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
        return queryUniversePort.searchUniverses(command);
    }
}
