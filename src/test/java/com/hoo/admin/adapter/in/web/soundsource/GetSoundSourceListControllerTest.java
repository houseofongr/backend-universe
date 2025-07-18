package com.hoo.admin.adapter.in.web.soundsource;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetSoundSourceListControllerTest extends AbstractControllerTest {

    @Test
    @Sql("GetSoundSourceListControllerTest.sql")
    @DisplayName("음원 리스트 조회 API")
    void testGetSoundSourceListAPI() throws Exception {
        mockMvc.perform(get("/admin/sound-sources")
                        .param("page", "1")
                        .param("size", "3"))
                .andExpect(status().is(200))
                .andDo(document("admin-soundsource-get-list",
                        pathParameters(
                                parameterWithName("page").description("보여줄 페이지 번호입니다. +" + "\n" + "* 기본값 : 1").optional(),
                                parameterWithName("size").description("한 페이지에 보여줄 데이터 개수입니다. +" + "\n" + "* 기본값 : 10").optional()
                        ),
                        responseFields(
                                fieldWithPath("soundSources[].name").description("음원의 이름입니다."),
                                fieldWithPath("soundSources[].description").description("음원의 상세설명입니다."),
                                fieldWithPath("soundSources[].createdDate").description("음원의 생성일입니다."),
                                fieldWithPath("soundSources[].updatedDate").description("음원의 수정일입니다."),
                                fieldWithPath("soundSources[].audioFileId").description("음원이 보유한 음악 파일 ID입니다."),
                                fieldWithPath("soundSources[].isActive").description("음원의 활성화 여부입니다."),
                                fieldWithPath("soundSources[].userNickname").description("음원을 보유한 사용자의 닉네임입니다."),
                                fieldWithPath("soundSources[].userEmail").description("음원을 보유한 사용자의 이메일입니다."),
                                fieldWithPath("soundSources[].userId").description("음원을 보유한 사용자의 ID입니다."),
                                fieldWithPath("soundSources[].homeName").description("음원이 위치한 홈 이름입니다."),
                                fieldWithPath("soundSources[].homeId").description("음원이 위치한 홈 ID입니다."),
                                fieldWithPath("soundSources[].roomName").description("음원이 위치한 방 이름입니다."),
                                fieldWithPath("soundSources[].roomId").description("음원이 위치한 룸 ID입니다."),
                                fieldWithPath("soundSources[].itemName").description("음원이 위치한 아이템 이름입니다."),
                                fieldWithPath("soundSources[].itemId").description("음원이 위치한 아이템 ID입니다."),

                                fieldWithPath("pagination.pageNumber").description("현재 페이지 번호입니다."),
                                fieldWithPath("pagination.size").description("한 페이지의 항목 수입니다."),
                                fieldWithPath("pagination.totalElements").description("조회된 전체 개수입니다."),
                                fieldWithPath("pagination.totalPages").description("조회된 전체 페이지 개수입니다.")
                        )
                ));
    }
}