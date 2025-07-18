package com.hoo.admin.adapter.in.web.item;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeleteItemControllerTest extends AbstractControllerTest {

    @Test
    @Sql("DeleteItemControllerTest.sql")
    @DisplayName("아이템 삭제 API")
    void testDeleteItemAPI() throws Exception {
        mockMvc.perform(delete("/admin/items/{itemId}", 1L))
                .andExpect(status().is(200))
                .andDo(document("admin-item-delete",
                        pathParameters(
                                parameterWithName("itemId").description("삭제할 아이템의 ID입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("삭제 완료 메시지 : 0번 아이템이 삭제되었습니다.")
                        )
                ));
    }
}