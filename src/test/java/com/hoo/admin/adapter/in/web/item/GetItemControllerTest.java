package com.hoo.admin.adapter.in.web.item;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetItemControllerTest extends AbstractControllerTest {


    @Test
    @Sql("GetItemControllerTest.sql")
    @DisplayName("아이템 조회 API")
    void testGetItem() throws Exception {
        mockMvc.perform(get("/admin/items/{itemId}/sound-sources", 1L))
                .andExpect(status().is(200))
                .andDo(document("admin-item-get",
                        pathParameters(
                                parameterWithName("itemId").description("조회할 아이템의 ID입니다.")
                        ),
                        responseFields(
                                fieldWithPath("itemName").description("아이템의 이름입니다."),
                                fieldWithPath("soundSource[].id").description("음원의 ID입니다."),
                                fieldWithPath("soundSource[].name").description("음원의 이름입니다."),
                                fieldWithPath("soundSource[].description").description("음원에 대한 설명입니다."),
                                fieldWithPath("soundSource[].createdDate").description("음원의 생성일입니다."),
                                fieldWithPath("soundSource[].updatedDate").description("음원의 최종 수정일입니다."),
                                fieldWithPath("soundSource[].audioFileId").description("음원파일의 ID입니다.")
                        )
                ));
    }
}