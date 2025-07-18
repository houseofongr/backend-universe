package com.hoo.universe.api.out.persistence;

import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.common.web.dto.PageRequest;
import com.hoo.universe.api.dto.command.SearchUniverseCommand;
import com.hoo.universe.api.dto.query.OpenPieceQueryResult;
import com.hoo.universe.api.dto.query.UniverseListQueryInfo;
import com.hoo.universe.api.dto.result.UniverseListInfo;
import com.hoo.universe.api.dto.result.piece.OpenPieceResult;

import java.util.UUID;

public interface QueryUniversePort {

    PageQueryResult<UniverseListQueryInfo> searchUniverses(SearchUniverseCommand command);

    OpenPieceQueryResult searchPiece(UUID pieceID, PageRequest pageRequest);
}
