package com.hoo.admin.application.service.piece;

import com.hoo.admin.application.port.in.piece.SearchPieceCommand;
import com.hoo.admin.application.port.in.piece.SearchPieceResult;
import com.hoo.admin.application.port.out.piece.FindPiecePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import static org.mockito.Mockito.*;

class SearchPieceServiceTest {

    FindPiecePort findPiecePort = mock();
    SearchPieceService sut = new SearchPieceService(findPiecePort);

    @Test
    @DisplayName("피스 검색 서비스")
    void testSearchPieceTest() {
        // given
        SearchPieceCommand command = new SearchPieceCommand(Pageable.ofSize(10), 1L, "life", false);

        // when
        SearchPieceResult search = sut.search(command);

        // then
        verify(findPiecePort, times(1)).search(command);
    }

}