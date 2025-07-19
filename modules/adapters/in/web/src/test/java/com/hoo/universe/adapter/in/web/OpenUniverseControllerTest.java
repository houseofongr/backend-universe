package com.hoo.universe.adapter.in.web;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.api.in.web.dto.result.OpenUniverseResult;
import com.hoo.universe.api.in.web.usecase.OpenUniverseUseCase;
import com.hoo.universe.domain.vo.Category;
import com.hoo.universe.domain.vo.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.util.List;
import java.util.UUID;

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

        when(openUniverseUseCase.openUniverseWithComponents(universeID)).thenReturn(getResult());

        mockMvc.perform(get("/universes/{universeID}", universeID))
                .andExpect(status().is(200))
                .andDo(document("open-universe",
                        pathParameters(
                                parameterWithName("universeID").description("조회할 유니버스의 ID입니다.")
                        ),
                        responseFields(
                                fieldWithPath("id").description("유니버스의 아이디입니다."),
                                fieldWithPath("thumbmusicFileUrl").description("썸뮤직 파일 ID입니다."),
                                fieldWithPath("thumbnailFileUrl").description("썸네일 파일 ID입니다."),
                                fieldWithPath("backgroundFileUrl").description("백그라운드 파일 ID입니다."),
                                fieldWithPath("ownerID").description("작성자의 ID입니다."),
                                fieldWithPath("createdTime").description(" 생성(등록)일자입니다."),
                                fieldWithPath("updatedTime").description(" 수정일자입니다."),
                                fieldWithPath("view").description("조회수입니다."),
                                fieldWithPath("like").description("좋아요 숫자입니다."),
                                fieldWithPath("title").description("제목입니다."),
                                fieldWithPath("owner").description("작성자의 닉네임입니다."),
                                fieldWithPath("description").description("설명입니다."),
                                fieldWithPath("accessLevel").description("공개 여부입니다."),
                                fieldWithPath("hashtags").description("해시태그 리스트입니다."),
                                fieldWithPath("category.id").description("카테고리의 ID입니다."),
                                fieldWithPath("category.eng").description("카테고리의 영문 이름입니다."),
                                fieldWithPath("category.kor").description("카테고리의 한글 이름입니다."),
                                fieldWithPath("spaces").description("유니버스 내부의 스페이스입니다."),
                                fieldWithPath("pieces").description("유니버스 내부의 피스입니다."),

                                fieldWithPath("spaces[].spaceID").description("스페이스의 ID입니다."),
                                fieldWithPath("spaces[].parentSpaceID").description("스페이스의 부모 스페이스 ID입니다. +" + "\n" + "* 부모가 유니버스일 경우, -1"),
                                fieldWithPath("spaces[].backgroundFileUrl").description("스페이스의 백그라운드 파일 ID입니다."),
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
                                fieldWithPath("pieces[].imageFileUrl").description("피스의 이미지파일 ID입니다."),
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

    private OpenUniverseResult getResult() {
        return new OpenUniverseResult(
                UUID.randomUUID(),
                URI.create("http://example.com/files/thumbmusic.mp3"),
                URI.create("http://example.com/files/thumbnail.jpg"),
                URI.create("http://example.com/files/background.jpg"),
                UUID.randomUUID(),
                System.currentTimeMillis() / 1000,
                System.currentTimeMillis() / 1000,
                123L,
                20L,
                "열린 유니버스 제목",
                "열린 유니버스 내용",
                "leaf",
                "PUBLIC",
                new Category(UUID.randomUUID(), "category", "카테고리"),
                List.of("해", "시", "태그"),
                List.of(
                        new OpenUniverseResult.SpaceInfo(
                                UUID.randomUUID(),
                                UUID.randomUUID(),
                                URI.create("http://example.com/files/space-bg.jpg"),
                                1,
                                "스페이스-1",
                                "스페이스-1 내용",
                                false,
                                List.of(
                                        new Point(0.1, 0.2),
                                        new Point(0.3, 0.4),
                                        new Point(0.5, 0.6)
                                ),
                                System.currentTimeMillis() / 1000,
                                System.currentTimeMillis() / 1000,
                                List.of(), // 하위 스페이스
                                List.of(
                                        new OpenUniverseResult.PieceInfo(
                                                UUID.randomUUID(),
                                                UUID.randomUUID(),
                                                URI.create("http://example.com/files/piece-img.png"),
                                                2,
                                                "피스-1",
                                                "피스-1 설명",
                                                false,
                                                List.of(
                                                        new Point(0.6, 0.7),
                                                        new Point(0.8, 0.9)
                                                ),
                                                System.currentTimeMillis() / 1000,
                                                System.currentTimeMillis() / 1000
                                        )
                                )
                        )
                ),
                List.of(
                        new OpenUniverseResult.PieceInfo(
                                UUID.randomUUID(),
                                null,
                                URI.create("http://example.com/files/top-piece.png"),
                                0,
                                "최상단 피스",
                                "최상단 피스 설명",
                                false,
                                List.of(
                                        new Point(0.0, 0.1),
                                        new Point(0.2, 0.3)
                                ),
                                System.currentTimeMillis() / 1000,
                                System.currentTimeMillis() / 1000
                        )
                )
        );
    }
}