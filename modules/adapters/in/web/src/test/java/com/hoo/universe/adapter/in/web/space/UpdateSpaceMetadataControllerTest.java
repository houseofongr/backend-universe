package com.hoo.universe.adapter.in.web.space;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.adapter.in.web.DocumentationTest;
import com.hoo.universe.api.in.web.dto.result.UpdateSpaceMetadataResult;
import com.hoo.universe.api.in.web.usecase.UpdateSpaceMetadataUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UpdateSpaceMetadataControllerTest extends DocumentationTest {

    @Autowired
    private UpdateSpaceMetadataUseCase updateSpaceMetadataUseCase;

    @Test
    @DisplayName("스페이스 상세정보 수정")
    void updateSpaceMetadata() throws Exception {

        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        UUID spaceID = UuidCreator.getTimeOrderedEpoch();
        //language=JSON
        String request = """
                {
                  "title": "수정할 제목을 입력합니다.",
                  "description": "수정할 내용을 입력합니다.",
                  "hidden": false
                }
                """;

        when(updateSpaceMetadataUseCase.updateSpaceMetadata(any(), any(), argThat(command ->
                command.title().equals("수정할 제목을 입력합니다.") &&
                command.description().equals("수정할 내용을 입력합니다.") &&
                command.hidden().equals(false))))
                .thenReturn(new UpdateSpaceMetadataResult(
                        "수정할 제목을 입력합니다.",
                        "수정할 내용을 입력합니다.",
                        false,
                        ZonedDateTime.now().toEpochSecond()
                ));

        mockMvc.perform(patch("/universes/{universeID}/spaces/{spaceID}", universeID, spaceID)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andDo(document("update-space-metadata",
                        pathParameters(
                                parameterWithName("universeID").description("수정할 스페이스를 소유한 유니버스의 ID입니다."),
                                parameterWithName("spaceID").description("수정할 스페이스의 ID입니다.")
                        ),
                        requestFields(
                                fieldWithPath("title").description("수정할 제목입니다.").optional(),
                                fieldWithPath("description").description("수정할 상세정보입니다.").optional(),
                                fieldWithPath("hidden").description("수정할 숨김 여부입니다.").optional()
                        ),
                        responseFields(
                                fieldWithPath("title").description("수정된 제목입니다."),
                                fieldWithPath("description").description("수정된 상세정보입니다."),
                                fieldWithPath("hidden").description("수정된 숨김 여부입니다."),
                                fieldWithPath("updatedTime").description("수정 일자입니다.")
                        )
                ));
    }

}