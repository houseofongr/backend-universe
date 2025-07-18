package com.hoo.admin.adapter.in.web.space;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import com.hoo.file.adapter.out.persistence.entity.FileJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:sql/universe.sql")
class PostSpaceControllerTest extends AbstractControllerTest {

    //language=JSON
    String metadata = """
            {
              "universeId": 1,
              "parentSpaceId": 1,
              "title": "공간",
              "description": "스페이스는 공간입니다.",
              "startX": 0.8,
              "startY": 0.7,
              "endX": 0.6,
              "endY": 0.5,
              "hidden": false
            }
            """;

    @Override
    public boolean useSpringSecurity() {
        return false;
    }

    @Test
    @DisplayName("스페이스 생성 API")
    void testCreateSpace() throws Exception {

        MockPart metadataPart = new MockPart("metadata", metadata.getBytes());
        metadataPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        MockMultipartFile image = new MockMultipartFile("innerImage", "inner_image.png", "image/png", "image file".getBytes());

        mockMvc.perform(multipart("/admin/spaces")
                        .file(image)
                        .part(metadataPart)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(201))
                .andDo(document("admin-space-post",
                        requestParts(
                                partWithName("metadata").description("생성할 스페이스의 정보를 포함하는 Json 형태의 문자열입니다."),
                                partWithName("innerImage").description("생성할 스페이스의 내부 이미지입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("생성 완료 메시지 : '[#id]번 스페이스가 생성되었습니다.'"),
                                fieldWithPath("spaceId").description("생성된 스페이스의 ID입니다."),
                                fieldWithPath("innerImageId").description("생성된 스페이스의 내부 이미지입니다."),
                                fieldWithPath("title").description("생성된 스페이스의 제목입니다."),
                                fieldWithPath("description").description("생성된 스페이스의 상세정보입니다."),
                                fieldWithPath("startX").description("생성된 스페이스의 시작좌표(x)입니다."),
                                fieldWithPath("startY").description("생성된 스페이스의 시작좌표(y)입니다."),
                                fieldWithPath("endX").description("생성된 스페이스의 종료좌표(x)입니다."),
                                fieldWithPath("endY").description("생성된 스페이스의 종료좌표(y)입니다."),
                                fieldWithPath("hidden").description("생성된 스페이스의 숨김 여부입니다.")
                        )
                ));

        List<FileJpaEntity> fileInDB = fileJpaRepository.findAll();
        assertThat(fileInDB).hasSize(1);
        assertThat(fileInDB)
                .anySatisfy(fileJpaEntity -> assertThat(fileJpaEntity.getRealFileName()).isEqualTo("inner_image.png"));
    }

}