package com.hoo.admin.adapter.in.web.universe;

import com.hoo.admin.application.port.in.universe.SearchUniverseCommand;
import com.hoo.admin.application.port.in.universe.SearchUniverseResult;
import com.hoo.admin.application.port.in.universe.SearchUniverseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetUniverseListController {

    private final SearchUniverseUseCase useCase;

    @GetMapping("/admin/universes")
    public ResponseEntity<SearchUniverseResult> search(
            Pageable pageable,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sortType,
            @RequestParam(required = false) Boolean isAsc
    ) {
        return ResponseEntity.ok(useCase.search(new SearchUniverseCommand(pageable, categoryId, searchType, keyword, sortType, isAsc)));
    }
}
