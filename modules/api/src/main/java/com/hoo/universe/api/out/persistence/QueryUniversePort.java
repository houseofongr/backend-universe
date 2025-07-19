package com.hoo.universe.api.out.persistence;

import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.common.web.dto.PageRequest;
import com.hoo.universe.api.in.web.dto.command.SearchUniverseCommand;
import com.hoo.universe.api.in.web.dto.query.OpenPieceQueryResult;
import com.hoo.universe.api.in.web.dto.query.UniverseListQueryInfo;

import java.util.UUID;

public interface QueryUniversePort {

    PageQueryResult<UniverseListQueryInfo> searchUniverses(SearchUniverseCommand command);

    OpenPieceQueryResult searchPiece(UUID pieceID, PageRequest pageRequest);
}
