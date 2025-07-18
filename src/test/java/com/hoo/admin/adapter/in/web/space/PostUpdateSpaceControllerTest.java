package com.hoo.admin.adapter.in.web.space;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import com.hoo.common.adapter.out.persistence.repository.SpaceJpaRepository;
import com.hoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.hoo.file.domain.FileType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:sql/universe.sql")
class PostUpdateSpaceControllerTest extends AbstractControllerTest {

    @Autowired
    SpaceJpaRepository spaceJpaRepository;

    @Test
    @Transactional
    @DisplayName("스페이스 내부이미지 수정 API")
    void testUpdateSpaceInnerImageAPI() throws Exception {
        saveFile(37L, FileType.IMAGE);
        MockMultipartFile innerImage = new MockMultipartFile("innerImage", "new_space_inner_image.png", "image/png", "space file".getBytes());

        mockMvc.perform(multipart("/admin/spaces/inner-image/{spaceId}", 1)
                        .file(innerImage)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-space-post-update-inner-image",
                        pathParameters(
                                parameterWithName("spaceId").description("수정할 스페이스의 ID입니다.")
                        ),
                        requestParts(
                                partWithName("innerImage").description("수정할 스페이스의 내부 이미지입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("수정 완료 메시지 : '[#id]번 스페이스의 내부 이미지가 수정되었습니다.'"),
                                fieldWithPath("deletedInnerImageId").description("삭제된 내부 이미지 아이디입니다."),
                                fieldWithPath("newInnerImageId").description("새로운 내부 이미지 아이디입니다.")
                        )
                ));

        List<FileJpaEntity> fileInDB = fileJpaRepository.findAll();
        assertThat(fileInDB).hasSize(1);
        assertThat(fileInDB)
                .anySatisfy(fileJpaEntity -> assertThat(fileJpaEntity.getRealFileName()).isEqualTo("new_space_inner_image.png"));
        assertThat(spaceJpaRepository.findById(1L).orElseThrow().getInnerImageFileId()).isEqualTo(fileInDB.getLast().getId());
    }
}