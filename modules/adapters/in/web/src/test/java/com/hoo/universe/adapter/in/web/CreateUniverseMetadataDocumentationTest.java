package com.hoo.universe.adapter.in.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Import(CreateUniverseMetadataDocumentationController.class)
public class CreateUniverseMetadataDocumentationTest extends DocumentationTest {

    @Test
    @DisplayName("유니버스 생성 메타데이터 문서화")
    void createUniverseMetadataDocumentation() throws Exception {
        mockMvc.perform(get("/universes/create-universe-metadata"))
                .andDo(document("create-universe-metadata",
                        responseFields(
                                fieldWithPath("title").description("생성할 유니버스의 제목입니다."),
                                fieldWithPath("description").description("생성할 유니버스의 상세정보입니다."),
                                fieldWithPath("ownerID").description("생성할 유니버스의 작성자 ID입니다."),
                                fieldWithPath("categoryID").description("생성할 유니버스의 카테고리 ID입니다."),
                                fieldWithPath("accessLevel").description("생성할 유니버스의 접근상태 정보입니다. +" + "\n" +
                                                                          "* PUBLIC : 공개 유니버스, PRIVATE : 비공개 유니버스"),
                                fieldWithPath("hashtags").description("생성할 유니버스의 해시태그입니다.")
                        )
                ));
    }
}
