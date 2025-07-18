package com.hoo.universe.adapter.in.web.space;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.adapter.in.web.DocumentationTest;
import com.hoo.universe.api.dto.result.space.CreateSpaceResult;
import com.hoo.universe.api.in.space.CreateSpaceUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreateSpaceControllerTest extends DocumentationTest {

    @Autowired
    private CreateSpaceUseCase createSpaceUseCase;

    @Test
    @DisplayName("스페이스 생성 API")
    void testCreateSpace() throws Exception {

        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        UUID parentSpaceID = UuidCreator.getTimeOrderedEpoch();
        //language=JSON
        String metadata = String.format("""
                {
                  "parentSpaceID": "%s",
                  "title": "공간",
                  "description": "스페이스는 공간입니다.",
                  "startX": 0.8,
                  "startY": 0.7,
                  "endX": 0.6,
                  "endY": 0.5,
                  "hidden": false
                }
                """, parentSpaceID);

        when(createSpaceUseCase.createSpaceWithTwoPoint(any(), any(), argThat(command ->
                command.metadata().title().equals("공간") &&
                command.metadata().description().equals("스페이스는 공간입니다.") &&
                command.metadata().startX().equals(0.8) &&
                command.metadata().startY().equals(0.7) &&
                command.metadata().endX().equals(0.6) &&
                command.metadata().endY().equals(0.5) &&
                command.metadata().hidden().equals(false)
        )))
                .thenReturn(new CreateSpaceResult(
                        UuidCreator.getTimeOrderedEpoch(),
                        UuidCreator.getTimeOrderedEpoch(),
                        "공간",
                        "스페이스는 공간입니다.",
                        0.8,
                        0.7,
                        0.6,
                        0.5,
                        false,
                        ZonedDateTime.now().toEpochSecond()
                ));

        MockPart metadataPart = new MockPart("metadata", metadata.getBytes());
        metadataPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        MockMultipartFile background = new MockMultipartFile("background", "background.png", "image/png", "image file".getBytes());

        mockMvc.perform(multipart("/universes/{universeID}/spaces", universeID)
                        .file(background)
                        .part(metadataPart)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is(201))
                .andDo(document("create-space",
                        pathParameters(
                                parameterWithName("universeID").description("생성할 스페이스를 소유한 유니버스 ID입니다.")
                        ),
                        requestParts(
                                partWithName("metadata").description("생성할 스페이스의 정보를 포함하는 Json 형태의 문자열입니다."),
                                partWithName("background").description("생성할 스페이스의 배경 파일입니다.")
                        ),
                        responseFields(
                                fieldWithPath("spaceID").description("생성된 스페이스의 ID입니다."),
                                fieldWithPath("backgroundID").description("생성된 스페이스의 내부 배경입니다."),
                                fieldWithPath("title").description("생성된 스페이스의 제목입니다."),
                                fieldWithPath("description").description("생성된 스페이스의 상세정보입니다."),
                                fieldWithPath("startX").description("생성된 스페이스의 시작좌표(x)입니다."),
                                fieldWithPath("startY").description("생성된 스페이스의 시작좌표(y)입니다."),
                                fieldWithPath("endX").description("생성된 스페이스의 종료좌표(x)입니다."),
                                fieldWithPath("endY").description("생성된 스페이스의 종료좌표(y)입니다."),
                                fieldWithPath("hidden").description("생성된 스페이스의 숨김 여부입니다."),
                                fieldWithPath("createdTime").description("생성 일자입니다.")
                        )
                ));
    }

}