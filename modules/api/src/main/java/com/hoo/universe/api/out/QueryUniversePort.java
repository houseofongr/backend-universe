package com.hoo.universe.api.out;

import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.common.web.dto.PageRequest;
import com.hoo.universe.api.in.dto.SearchUniverseCommand;
import com.hoo.universe.api.out.dto.OpenPieceQueryResult;
import com.hoo.universe.api.out.dto.UniverseListQueryInfo;

import java.util.UUID;

public interface QueryUniversePort {
    PageQueryResult<UniverseListQueryInfo> searchUniverses(SearchUniverseCommand command);

    OpenPieceQueryResult searchPiece(UUID pieceID, PageRequest pageRequest);
}
