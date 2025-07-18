package com.hoo.admin.adapter.in.web.house;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import com.hoo.file.adapter.out.persistence.repository.FileJpaRepository;
import com.hoo.file.domain.FileF;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeleteHouseControllerTest extends AbstractControllerTest {

    @Autowired
    FileJpaRepository fileJpaRepository;

    @Test
    @Sql("DeleteHouseControllerTest.sql")
    @DisplayName("하우스 삭제 API")
    void testDeleteHouse() throws Exception {

        saveFile(FileF.IMAGE_FILE_1.get(tempDir.toString()));
        saveFile(FileF.IMAGE_FILE_2.get(tempDir.toString()));
        saveFile(FileF.IMAGE_FILE_3.get(tempDir.toString()));
        saveFile(FileF.IMAGE_FILE_4.get(tempDir.toString()));

        mockMvc.perform(delete("/admin/houses/{houseId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("admin-house-delete",
                        pathParameters(
                                parameterWithName("houseId").description("삭제할 하우스의 ID입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("삭제 완료 메시지 : 0번 하우스가 삭제되었습니다.")
                        )
                ));
    }

}