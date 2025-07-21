package com.hoo.universe.adapter.in.web;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.universe.api.in.dto.UpdateUniverseMetadataResult;
import com.hoo.universe.api.in.UpdateUniverseMetadataUseCase;
import com.hoo.universe.domain.vo.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UpdateUniverseMetadataControllerTest extends DocumentationTest {

    @Autowired
    private UpdateUniverseMetadataUseCase updateUniverseMetadataUseCase;

    @Test
    @DisplayName("유니버스 상세정보 수정")
    void updateUniverseMetadata() throws Exception {
        //language=JSON
        String request = """
                {
                  "title": "수정된 제목",
                  "description": "수정된 내용",
                  "ownerID": "0198132d-a951-7696-ae2f-f18c41996c2b",
                  "categoryID": "0198132d-a951-7cd6-9f71-5b130384fd86",
                  "accessLevel": "PRIVATE",
                  "hashtags": [
                    "수정된", "해시태그"
                  ]
                }
                """;

        UUID universeID = UuidCreator.getTimeOrderedEpoch();
        when(updateUniverseMetadataUseCase.updateUniverseMetadata(any(), argThat(command ->
                command.title().equals("수정된 제목") &&
                command.description().equals("수정된 내용") &&
                command.ownerID().toString().equals("0198132d-a951-7696-ae2f-f18c41996c2b") &&
                command.categoryID().toString().equals("0198132d-a951-7cd6-9f71-5b130384fd86") &&
                command.accessLevel().equals("PRIVATE") &&
                command.hashtags().containsAll(List.of("수정된", "해시태그")))))
                .thenReturn(new UpdateUniverseMetadataResult(
                        universeID,
                        ZonedDateTime.now().toEpochSecond(),
                        "수정된 제목",
                        "수정된 내용",
                        "수정된 저자",
                        "PRIVATE",
                        new Category(
                                UUID.fromString("0198132d-a951-7cd6-9f71-5b130384fd86"),
                                "altered category",
                                "수정된 카테고리"
                        ),
                        List.of("수정된", "해시태그")
                ));


        mockMvc.perform(patch("/universes/{universeID}", universeID)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andDo(document("update-universe-metadata",
                        pathParameters(
                                parameterWithName("universeID").description("수정할 유니버스의 ID입니다.")
                        ),
                        requestFields(
                                fieldWithPath("title").description("수정할 제목입니다."),
                                fieldWithPath("description").description("수정할 상세정보입니다."),
                                fieldWithPath("ownerID").description("수정할 작성자 ID입니다."),
                                fieldWithPath("categoryID").description("수정할 카테고리 ID입니다."),
                                fieldWithPath("accessLevel").description("공개 여부입니다."),
                                fieldWithPath("hashtags").description("수정할 태그 정보입니다.")
                        ),
                        responseFields(
                                fieldWithPath("ownerID").description("수정된 작성자입니다."),
                                fieldWithPath("updatedTime").description(" 수정일자입니다."),
                                fieldWithPath("title").description("수정된 제목입니다."),
                                fieldWithPath("description").description("수정된 상세정보입니다."),
                                fieldWithPath("owner").description("수정된 작성자의 닉네임입니다."),
                                fieldWithPath("accessLevel").description("수정된 공개 여부입니다."),
                                fieldWithPath("hashtags").description("수정된 태그 정보입니다."),
                                fieldWithPath("category.id").description("수정된 카테고리의 ID입니다."),
                                fieldWithPath("category.eng").description("수정된 카테고리의 영문 이름입니다."),
                                fieldWithPath("category.kor").description("수정된 카테고리의 한글 이름입니다.")
                        )
                ));
    }

}