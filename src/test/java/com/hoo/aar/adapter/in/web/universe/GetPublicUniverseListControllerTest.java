package com.hoo.aar.adapter.in.web.universe;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.ownerity.SimpleGrantedAuthority;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:/sql/clear.sql")
@Sql("classpath:/sql/universe.sql")
class GetPublicUniverseListControllerTest extends AbstractControllerTest {

    @Test
    @DisplayName("공개 유니버스 리스트 조회 API")
    void testGetList() throws Exception {
        mockMvc.perform(get("/aar/universes?page=1&size=10"))
                .andExpect(status().is(200));

        mockMvc.perform(get("/aar/universes?page=1&size=10")
                        .with(jwt().jwt(jwt -> jwt.claim("userId", 1L))
                                .ownerities(new SimpleGrantedAuthority("ROLE_USER"))
                        )
                )
                .andExpect(status().is(200))
                .andDo(document("aar-public-universe-get-list",
                        pathParameters(
                                parameterWithName("page").description("보여줄 페이지 번호입니다. +"
                                                                      + "\n" + "* 기본값 : 1").optional(),
                                parameterWithName("size").description("한 페이지에 보여줄 데이터 개수입니다. +"
                                                                      + "\n" + "* 기본값 : 10").optional(),
                                parameterWithName("searchType").description("검색 방법입니다. +"
                                                                            + "\n" + "[CONTENT : 제목 + 내용 검색] +"
                                                                            + "\n" + "[AUTHOR : 작성자 검색] +"
                                                                            + "\n" + "[ALL : 작성자 + 제목 + 내용 전체 검색]").optional(),
                                parameterWithName("keyword").description("검색 키워드입니다.").optional(),
                                parameterWithName("sortType").description("정렬 방법입니다. +"
                                                                          + "\n" + "[TITLE : 제목 순] +"
                                                                          + "\n" + "[REGISTERED_DATE : 생성시간 순] +"
                                                                          + "\n" + "[VIEWS : 조회수 순]"
                                                                          + "\n" + "* 기본 정렬 : 생성시간 내림차순").optional(),
                                parameterWithName("isAsc").description("정렬 시 오름차순인지 여부입니다.").optional()
                        ),
                        responseFields(
                                fieldWithPath("universes[].id").description("유니버스의 아이디입니다."),
                                fieldWithPath("universes[].thumbnailId").description("썸네일 파일 ID입니다."),
                                fieldWithPath("universes[].thumbMusicId").description("썸뮤직 파일 ID입니다."),
                                fieldWithPath("universes[].ownerId").description("작성자의 ID입니다."),
                                fieldWithPath("universes[].createdTime").description("유닉스 타임스탬프 형식의 생성(등록)일자입니다."),
                                fieldWithPath("universes[].view").description("조회수입니다."),
                                fieldWithPath("universes[].likeCnt").description("좋아요 숫자입니다."),
                                fieldWithPath("universes[].isLiked").description("해당 게시글에 좋아요를 눌렀는지 여부입니다. * 로그인 시에만 동작"),
                                fieldWithPath("universes[].title").description("제목입니다."),
                                fieldWithPath("universes[].description").description("설명입니다."),
                                fieldWithPath("universes[].owner").description("작성자의 닉네임입니다."),
                                fieldWithPath("universes[].hashtags").description("해시태그 리스트입니다."),

                                fieldWithPath("pagination.pageNumber").description("현재 페이지 번호입니다."),
                                fieldWithPath("pagination.size").description("한 페이지의 항목 수입니다."),
                                fieldWithPath("pagination.totalElements").description("조회된 전체 개수입니다."),
                                fieldWithPath("pagination.totalPages").description("조회된 전체 페이지 개수입니다.")
                        )
                ));
    }


}