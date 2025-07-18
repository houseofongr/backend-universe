package com.hoo.universe.adapter.in.web;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.common.web.dto.PageRequest;
import com.hoo.universe.api.dto.result.UniverseListInfo;
import com.hoo.universe.api.in.SearchUniverseUseCase;
import com.hoo.universe.domain.vo.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SearchUniverseControllerTest extends DocumentationTest {

    @Autowired
    private SearchUniverseUseCase searchUniverseUseCase;

    @Test
    @DisplayName("유니버스 리스트 조회 API")
    void testGetList() throws Exception {

        when(searchUniverseUseCase.searchUniverse(any()))
                .thenReturn(PageQueryResult.from(
                                PageRequest.defaultPage(),
                                1L,
                                List.of(new UniverseListInfo(
                                        UuidCreator.getTimeOrderedEpoch(),
                                        URI.create("http://example.com/files/thumbmusic.mp3"),
                                        URI.create("http://example.com/files/thumbnail.jpg"),
                                        UuidCreator.getTimeOrderedEpoch(),
                                        ZonedDateTime.now().toEpochSecond(),
                                        ZonedDateTime.now().toEpochSecond(),
                                        123L,
                                        19,
                                        "조회된 유니버스 제목",
                                        "조회된 유니버스 내용",
                                        "leaf",
                                        "PUBLIC",
                                        new Category(
                                                UuidCreator.getTimeOrderedEpoch(),
                                                "retried category",
                                                "조회된 카테고리"),
                                        List.of("조회된", "해시태그")
                                ))));

        mockMvc.perform(get("/universes?page=1&size=10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(200))
                .andDo(document("search-universe",
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
                                parameterWithName("isAsc").description("정렬 시 오름차순인지 여부입니다.").optional(),
                                parameterWithName("category").description("필터링할 카테고리 ID입니다.").optional()
                        ),
                        responseFields(
                                fieldWithPath("content[].id").description("유니버스의 아이디입니다."),
                                fieldWithPath("content[].thumbnailFileUrl").description("썸네일 파일 ID입니다."),
                                fieldWithPath("content[].thumbmusicFileUrl").description("썸뮤직 파일 ID입니다."),
                                fieldWithPath("content[].ownerID").description("작성자의 ID입니다."),
                                fieldWithPath("content[].createdTime").description(" 생성(등록)일자입니다."),
                                fieldWithPath("content[].updatedTime").description(" 생성(등록)일자입니다."),
                                fieldWithPath("content[].view").description("조회수입니다."),
                                fieldWithPath("content[].like").description("좋아요 숫자입니다."),
                                fieldWithPath("content[].title").description("제목입니다."),
                                fieldWithPath("content[].owner").description("작성자의 닉네임입니다."),
                                fieldWithPath("content[].description").description("설명입니다."),
                                fieldWithPath("content[].category").description("카테고리입니다."),
                                fieldWithPath("content[].accessLevel").description("공개 여부입니다."),
                                fieldWithPath("content[].hashtags").description("해시태그 리스트입니다."),
                                fieldWithPath("content[].category.id").description("카테고리의 ID입니다."),
                                fieldWithPath("content[].category.eng").description("카테고리의 영문 이름입니다."),
                                fieldWithPath("content[].category.kor").description("카테고리의 한글 이름입니다."),

                                fieldWithPath("pagination.page").description("현재 페이지 번호입니다."),
                                fieldWithPath("pagination.size").description("한 페이지의 항목 수입니다."),
                                fieldWithPath("pagination.totalPages").description("조회된 전체 페이지 개수입니다."),
                                fieldWithPath("pagination.totalElements").description("조회된 전체 개수입니다.")
                        )
                ));
    }
}