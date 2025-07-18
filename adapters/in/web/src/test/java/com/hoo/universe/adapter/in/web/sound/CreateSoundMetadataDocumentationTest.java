package com.hoo.universe.adapter.in.web.sound;

import com.hoo.universe.adapter.in.web.DocumentationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Import(CreateSoundMetadataDocumentationController.class)
public class CreateSoundMetadataDocumentationTest extends DocumentationTest {

    @Test
    @DisplayName("사운드 생성 메타데이터 문서화")
    void createSoundMetadataDocumentation() throws Exception {
        mockMvc.perform(get("/universes/create-sound-metadata"))
                .andDo(document("create-sound-metadata",
                        responseFields(
                                fieldWithPath("title").description("생성할 사운드의 제목입니다."),
                                fieldWithPath("description").description("생성할 사운드의 상세정보입니다."),
                                fieldWithPath("hidden").description("생성할 사운드의 숨김 여부입니다.")
                        )
                ));
    }
}
