package com.hoo.universe.adapter.in.web.space;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.adapter.in.web.DocumentationTest;
import com.hoo.universe.api.dto.result.space.OverwriteSpaceFileResult;
import com.hoo.universe.api.in.space.OverwriteSpaceFileUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.net.URI;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OverwriteSpaceFileControllerTest extends DocumentationTest {

    @Autowired
    private OverwriteSpaceFileUseCase overwriteSpaceFileUseCase;

    @Test
    @DisplayName("스페이스 파일 덮어쓰기")
    void overwriteSpaceFile() throws Exception {

        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        UUID spaceID = UuidCreator.getTimeOrderedEpoch();
        MockMultipartFile background = new MockMultipartFile("background", "new_space_background.png", "image/png", "space file".getBytes());

        when(overwriteSpaceFileUseCase.overwriteSpaceFile(any(), any(), any()))
                .thenReturn(new OverwriteSpaceFileResult(URI.create("http://example.com/files/background.jpg")));

        mockMvc.perform(multipart("/universes/{universeID}/spaces/{spaceID}/background", universeID, spaceID)
                        .file(background)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is(200))
                .andDo(document("overwrite-space-file",
                        pathParameters(
                                parameterWithName("universeID").description("수정할 스페이스를 소유한 유니버스의 ID입니다."),
                                parameterWithName("spaceID").description("수정할 스페이스의 ID입니다.")
                        ),
                        requestParts(
                                partWithName("background").description("수정할 스페이스의 배경 파일입니다.")
                        ),
                        responseFields(
                                fieldWithPath("newBackgroundFileUrl").description("새로운 배경 파일 아이디입니다.")
                        )
                ));
    }
}