package com.hoo.aar.adapter.in.web.universe;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:sql/clear.sql")
@Sql("classpath:/sql/universe.sql")
class GetPublicUniverseControllerTest extends AbstractControllerTest {

    @Test
    @DisplayName("공개 유니버스 상세정보 조회 API")
    void testGetList() throws Exception {
        mockMvc.perform(get("/aar/universes/{universeId}", 1))
                .andExpect(status().is(200));

        mockMvc.perform(get("/aar/universes/{universeId}", 1)
                        .with(jwt().jwt(jwt -> jwt.claim("userId", 1L))
                                .authorities(new SimpleGrantedAuthority("ROLE_USER"))
                        )
                )
                .andExpect(status().is(200))
                .andDo(document("aar-public-universe-get",
                        pathParameters(
                                parameterWithName("universeId").description("조회할 유니버스의 ID입니다. +" + "\n" +
                                                                            "* 본인 소유가 아닌 비공개 유니버스는 조회할 수 없습니다.")
                        ),
                        responseFields(
                                fieldWithPath("universeId").description("유니버스의 ID입니다."),
                                fieldWithPath("thumbMusicId").description("썸뮤직 파일 ID입니다."),
                                fieldWithPath("thumbnailId").description("썸네일 파일 ID입니다."),
                                fieldWithPath("innerImageId").description("내부 이미지 파일 ID입니다."),
                                fieldWithPath("authorId").description("작성자의 ID입니다."),
                                fieldWithPath("createdTime").description("유닉스 타임스탬프 형식의 생성(등록)일자입니다."),
                                fieldWithPath("updatedTime").description("유닉스 타임스탬프 형식의 수정일자입니다."),
                                fieldWithPath("view").description("조회수입니다. +" + "\n" +
                                                                  "* 조회 시 조회수가 1 증가합니다."),
                                fieldWithPath("like").description("좋아요 숫자입니다."),
                                fieldWithPath("title").description("제목입니다."),
                                fieldWithPath("author").description("작성자의 닉네임입니다."),
                                fieldWithPath("description").description("설명입니다."),
                                fieldWithPath("category.id").description("카테고리의 ID입니다."),
                                fieldWithPath("category.eng").description("카테고리의 영문 이름입니다."),
                                fieldWithPath("category.kor").description("카테고리의 한글 이름입니다."),
                                fieldWithPath("isMine").description("본인의 유니버스인지 여부입니다. +" + "\n" +
                                                                    "* 로그인 시에만 동작합니다."),
                                fieldWithPath("isLiked").description("해당 게시글에 좋아요를 눌렀는지 여부입니다. +" + "\n" +
                                                                     "* 로그인 시에만 동작합니다."),
                                fieldWithPath("hashtags").description("해시태그 리스트입니다."),
                                fieldWithPath("spaces").description("유니버스 내부의 스페이스입니다."),
                                fieldWithPath("pieces").description("유니버스 내부의 피스입니다."),

                                fieldWithPath("spaces[].spaceId").description("스페이스의 ID입니다."),
                                fieldWithPath("spaces[].parentSpaceId").description("스페이스의 부모 스페이스 ID입니다. +" + "\n" + "* 부모가 유니버스일 경우, -1"),
                                fieldWithPath("spaces[].innerImageId").description("스페이스의 내부 이미지파일 ID입니다."),
                                fieldWithPath("spaces[].depth").description("스페이스의 깊이입니다."),
                                fieldWithPath("spaces[].title").description("스페이스의 제목입니다."),
                                fieldWithPath("spaces[].description").description("스페이스의 설명입니다."),
                                fieldWithPath("spaces[].startX").description("스페이스의 시작좌표(X)입니다."),
                                fieldWithPath("spaces[].startY").description("스페이스의 시작좌표(Y)입니다."),
                                fieldWithPath("spaces[].endX").description("스페이스의 종료좌표(X)입니다."),
                                fieldWithPath("spaces[].endY").description("스페이스의 종료좌표(Y)입니다."),
                                fieldWithPath("spaces[].createdTime").description("스페이스의 생성일자입니다.(유닉스 타임스태프)"),
                                fieldWithPath("spaces[].updatedTime").description("스페이스의 수정일자입니다.(유닉스 타임스태프)"),
                                subsectionWithPath("spaces[].pieces").description("스페이스 내부의 피스입니다."),
                                subsectionWithPath("spaces[].spaces").description("스페이스 내부의 스페이스입니다.(무한 depth)"),

                                fieldWithPath("pieces[].pieceId").description("피스의 ID입니다."),
                                fieldWithPath("pieces[].parentSpaceId").description("피스의 부모 스페이스 ID입니다. +" + "\n" + "* 부모가 유니버스일 경우, -1"),
                                fieldWithPath("pieces[].innerImageId").description("피스의 내부 이미지파일 ID입니다."),
                                fieldWithPath("pieces[].depth").description("피스의 깊이입니다."),
                                fieldWithPath("pieces[].title").description("피스의 제목입니다."),
                                fieldWithPath("pieces[].description").description("피스의 설명입니다."),
                                fieldWithPath("pieces[].startX").description("피스의 시작좌표(X)입니다."),
                                fieldWithPath("pieces[].startY").description("피스의 시작좌표(Y)입니다."),
                                fieldWithPath("pieces[].endX").description("피스의 종료좌표(X)입니다."),
                                fieldWithPath("pieces[].endY").description("피스의 종료좌표(Y)입니다."),
                                fieldWithPath("pieces[].createdTime").description("피스의 생성일자입니다.(유닉스 타임스태프)"),
                                fieldWithPath("pieces[].updatedTime").description("피스의 수정일자입니다.(유닉스 타임스태프)")
                        )
                ));
    }

}