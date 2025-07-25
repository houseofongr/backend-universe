package com.hoo.universe.application;

import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.universe.api.in.dto.SearchUniverseCommand;
import com.hoo.universe.api.in.dto.UniverseListInfo;
import com.hoo.universe.api.in.SearchUniverseUseCase;
import com.hoo.universe.api.out.FileUrlResolveInCase;
import com.hoo.universe.api.out.QueryUniversePort;
import com.hoo.universe.api.out.dto.UniverseListQueryInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchUniverseService implements SearchUniverseUseCase {

    private final QueryUniversePort queryUniversePort;
    private final FileUrlResolveInCase fileUrlResolveInCase;
    private final FileOwnerMapExtractor fileOwnerMapExtractor;
    private final ApplicationMapper applicationMapper;

    @Override
    public PageQueryResult<UniverseListInfo> searchUniverse(SearchUniverseCommand command) {
        PageQueryResult<UniverseListQueryInfo> queryResult = queryUniversePort.searchUniverses(command);
        Map<UUID, UUID> fileOwnerMap = fileOwnerMapExtractor.extractFileOwnerMap(queryResult);
        Map<UUID, URI> fileUrlMap = fileUrlResolveInCase.resolveBatch(fileOwnerMap);
        return applicationMapper.mapToUniverseListInfoPageQueryResult(queryResult, fileUrlMap);
    }

}
