package com.hoo.universe.adapter.in.web.piece;

import com.hoo.universe.adapter.in.web.DocumentationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Import(CreatePieceMetadataDocumentationController.class)
public class CreatePieceMetadataDocumentationTest extends DocumentationTest {

    @Test
    @DisplayName("피스 생성 메타데이터 문서화")
    void createPieceMetadataDocumentation() throws Exception {
        mockMvc.perform(get("/universes/create-piece-metadata"))
                .andDo(document("create-piece-metadata",
                        responseFields(
                                fieldWithPath("parentSpaceID").description("생성할 피스를 소유하는 부모 스페이스입니다. +" + "\n" +
                                                                           "* null일 시, 유니버스의 하위 피스로 생성됩니다."),
                                fieldWithPath("title").description("생성할 피스의 제목입니다."),
                                fieldWithPath("description").description("생성할 피스의 상세정보입니다."),
                                fieldWithPath("startX").description("생성할 피스의 시작좌표(x)입니다."),
                                fieldWithPath("startY").description("생성할 피스의 시작좌표(y)입니다."),
                                fieldWithPath("endX").description("생성할 피스의 종료좌표(x)입니다."),
                                fieldWithPath("endY").description("생성할 피스의 종료좌표(y)입니다."),
                                fieldWithPath("hidden").description("생성할 피스의 숨김 여부입니다.")
                        )
                ));
    }
}
