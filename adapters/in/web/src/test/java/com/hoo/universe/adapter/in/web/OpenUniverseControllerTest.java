package com.hoo.universe.adapter.in.web;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.api.dto.result.OpenUniverseResult;
import com.hoo.universe.api.in.OpenUniverseUseCase;
import com.hoo.universe.domain.Universe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static com.hoo.universe.test.domain.UniverseTestData.defaultUniverse;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OpenUniverseControllerTest extends DocumentationTest {

    @Autowired
    private OpenUniverseUseCase openUniverseUseCase;

    @Test
    @DisplayName("유니버스 열기")
    void openUniverse() throws Exception {

        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        Universe universe = defaultUniverse();

        when(openUniverseUseCase.openUniverseWithComponents(universeID))
                .thenReturn(OpenUniverseResult.from(universe));

        mockMvc.perform(get("/universes/{universeID}", universeID))
                .andExpect(status().is(200))
                .andDo(document("open-universe",
                        pathParameters(
                                parameterWithName("universeID").description("조회할 유니버스의 ID입니다.")
                        ),
                        responseFields(
                                fieldWithPath("id").description("유니버스의 아이디입니다."),
                                fieldWithPath("thumbnailID").description("썸네일 파일 ID입니다."),
                                fieldWithPath("thumbMusicID").description("썸뮤직 파일 ID입니다."),
                                fieldWithPath("backgroundID").description("백그라운드 파일 ID입니다."),
                                fieldWithPath("authorID").description("작성자의 ID입니다."),
                                fieldWithPath("createdTime").description(" 생성(등록)일자입니다."),
                                fieldWithPath("updatedTime").description(" 수정일자입니다."),
                                fieldWithPath("view").description("조회수입니다."),
                                fieldWithPath("like").description("좋아요 숫자입니다."),
                                fieldWithPath("title").description("제목입니다."),
                                fieldWithPath("author").description("작성자의 닉네임입니다."),
                                fieldWithPath("description").description("설명입니다."),
                                fieldWithPath("accessStatus").description("공개 여부입니다."),
                                fieldWithPath("hashtags").description("해시태그 리스트입니다."),
                                fieldWithPath("category.id").description("카테고리의 ID입니다."),
                                fieldWithPath("category.eng").description("카테고리의 영문 이름입니다."),
                                fieldWithPath("category.kor").description("카테고리의 한글 이름입니다."),
                                fieldWithPath("spaces").description("유니버스 내부의 스페이스입니다."),
                                fieldWithPath("pieces").description("유니버스 내부의 피스입니다."),

                                fieldWithPath("spaces[].spaceID").description("스페이스의 ID입니다."),
                                fieldWithPath("spaces[].parentSpaceID").description("스페이스의 부모 스페이스 ID입니다. +" + "\n" + "* 부모가 유니버스일 경우, -1"),
                                fieldWithPath("spaces[].backgroundID").description("스페이스의 백그라운드 파일 ID입니다."),
                                fieldWithPath("spaces[].depth").description("스페이스의 깊이입니다."),
                                fieldWithPath("spaces[].title").description("스페이스의 제목입니다."),
                                fieldWithPath("spaces[].description").description("스페이스의 설명입니다."),
                                fieldWithPath("spaces[].hidden").description("스페이스의 숨김여부입니다."),
                                fieldWithPath("spaces[].points[].x").description("스페이스의 외곽선 좌표(X)입니다."),
                                fieldWithPath("spaces[].points[].y").description("스페이스의 외곽선 좌표(Y)입니다."),
                                fieldWithPath("spaces[].createdTime").description("스페이스의 생성일자입니다.(유닉스 타임스태프)"),
                                fieldWithPath("spaces[].updatedTime").description("스페이스의 수정일자입니다.(유닉스 타임스태프)"),
                                subsectionWithPath("spaces[].pieces").description("스페이스 내부의 피스입니다."),
                                subsectionWithPath("spaces[].spaces").description("스페이스 내부의 스페이스입니다.(무한 depth)"),

                                fieldWithPath("pieces[].pieceID").description("피스의 ID입니다."),
                                fieldWithPath("pieces[].parentSpaceID").description("피스의 부모 스페이스 ID입니다. +" + "\n" + "* 부모가 유니버스일 경우, -1"),
                                fieldWithPath("pieces[].imageID").description("피스의 이미지파일 ID입니다."),
                                fieldWithPath("pieces[].depth").description("피스의 깊이입니다."),
                                fieldWithPath("pieces[].title").description("피스의 제목입니다."),
                                fieldWithPath("pieces[].description").description("피스의 설명입니다."),
                                fieldWithPath("pieces[].hidden").description("피스의 숨김여부입니다."),
                                fieldWithPath("pieces[].points[].x").description("피스의 외곽선 좌표(X)입니다."),
                                fieldWithPath("pieces[].points[].y").description("피스의 외곽선 좌표(Y)입니다."),
                                fieldWithPath("pieces[].createdTime").description("피스의 생성일자입니다.(유닉스 타임스태프)"),
                                fieldWithPath("pieces[].updatedTime").description("피스의 수정일자입니다.(유닉스 타임스태프)")
                        )
                ));
    }
}