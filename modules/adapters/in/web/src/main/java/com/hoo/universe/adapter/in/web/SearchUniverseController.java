package com.hoo.universe.adapter.in.web;

import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.common.web.dto.PageRequest;
import com.hoo.universe.api.in.dto.SearchUniverseCommand;
import com.hoo.universe.api.in.dto.UniverseListInfo;
import com.hoo.universe.api.in.SearchUniverseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SearchUniverseController {

    private final SearchUniverseUseCase useCase;
    private final WebMapper webMapper;

    @GetMapping("/universes")
    public ResponseEntity<PageQueryResult<UniverseListInfo>> search(
            Pageable pageable,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sortType,
            @RequestParam(required = false) Boolean isAsc,
            @RequestParam(required = false) UUID categoryID
    ) {

        PageRequest pageRequest = webMapper.mapToPageable(pageable, searchType, keyword, sortType, isAsc);
        return ResponseEntity.ok(useCase.searchUniverse(new SearchUniverseCommand(pageRequest, categoryID)));
    }
}
