package com.hoo.universe.adapter.in.web.space;

import com.hoo.universe.adapter.in.web.DocumentationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Import(CreateSpaceMetadataDocumentationController.class)
public class CreateSpaceMetadataDocumentationTest extends DocumentationTest {

    @Test
    @DisplayName("스페이스 생성 메타데이터 문서화")
    void createSpaceMetadataDocumentation() throws Exception {
        mockMvc.perform(get("/universes/create-space-metadata"))
                .andDo(document("create-space-metadata",
                        responseFields(
                                fieldWithPath("parentSpaceID").description("생성할 스페이스를 소유하는 부모 스페이스입니다. +" + "\n" +
                                                                           "* null일 시, 유니버스의 하위 스페이스로 생성됩니다."),
                                fieldWithPath("title").description("생성할 스페이스의 제목입니다."),
                                fieldWithPath("description").description("생성할 스페이스의 상세정보입니다."),
                                fieldWithPath("startX").description("생성할 스페이스의 시작좌표(x)입니다."),
                                fieldWithPath("startY").description("생성할 스페이스의 시작좌표(y)입니다."),
                                fieldWithPath("endX").description("생성할 스페이스의 종료좌표(x)입니다."),
                                fieldWithPath("endY").description("생성할 스페이스의 종료좌표(y)입니다."),
                                fieldWithPath("hidden").description("생성할 스페이스의 숨김 여부입니다.")
                        )
                ));
    }
}
