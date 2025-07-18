package com.hoo.admin.adapter.in.web.house;

import com.hoo.admin.application.port.in.house.CreateHouseMetadataTest;
import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import com.hoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.hoo.file.adapter.out.persistence.repository.FileJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostHouseControllerTest extends AbstractControllerTest {

    @Autowired
    FileJpaRepository fileJpaRepository;

    @Override
    public boolean useSpringSecurity() {
        return false;
    }

    @Test
    @DisplayName("잘못된 전송 타입일 때 오류")
    void testBadFileType() throws Exception {

        String metadata = CreateHouseMetadataTest.getCreateHouseMetadataJson();

        mockMvc.perform(post("/admin/houses")
                        .param("metadata", metadata)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(400))
                .andDo(document("admin-house-post-error-1"));
    }

    @Test
    @DisplayName("보더, 기본이미지 같을 때 오류")
    void testSameBorderAndImage() throws Exception {

        String metadata = CreateHouseMetadataTest.getCreateHouseMetadataJson();
        MockPart metadataPart = new MockPart("metadata", metadata.getBytes());
        metadataPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        MockMultipartFile houseImage = new MockMultipartFile("house", "image.png", "image/png", "<<png data 1>>".getBytes());
        MockMultipartFile borderImage = new MockMultipartFile("border", "image.png", "image/png", "<<png data 1>>".getBytes());
        MockMultipartFile roomImage1 = new MockMultipartFile("room1", "image3.png", "image/png", "<<png data 3>>".getBytes());
        MockMultipartFile roomImage2 = new MockMultipartFile("room2", "image4.png", "image/png", "<<png data 4>>".getBytes());

        mockMvc.perform(multipart("/admin/houses")
                        .file(houseImage).file(borderImage).file(roomImage1).file(roomImage2)
                        .part(metadataPart)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(400))
                .andDo(document("admin-house-post-error-2"));
    }

    @Test
    @DisplayName("하우스 생성 API")
    void testCreateHouse() throws Exception {

        String metadata = CreateHouseMetadataTest.getCreateHouseMetadataJson();
        MockPart metadataPart = new MockPart("metadata", metadata.getBytes());
        metadataPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        MockMultipartFile houseImage = new MockMultipartFile("house", "image.png", "image/png", "<<png data 1>>".getBytes());
        MockMultipartFile borderImage = new MockMultipartFile("border", "image2.png", "image/png", "<<png data 2>>".getBytes());
        MockMultipartFile roomImage1 = new MockMultipartFile("room1", "image3.png", "image/png", "<<png data 3>>".getBytes());
        MockMultipartFile roomImage2 = new MockMultipartFile("room2", "image4.png", "image/png", "<<png data 4>>".getBytes());

        mockMvc.perform(multipart("/admin/houses")
                        .file(houseImage).file(borderImage).file(roomImage1).file(roomImage2)
                        .part(metadataPart)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(201))
                .andDo(document("admin-house-post",
                        requestParts(
                                partWithName("metadata").description("생성할 하우스의 정보를 포함하는 Json 형태의 문자열입니다."),
                                partWithName("house").description("생성할 하우스의 기본 이미지입니다."),
                                partWithName("border").description("생성할 하우스의 기본 외곽 이미지입니다."),
                                partWithName("room1").description("첫번째 룸의 기본 이미지입니다."),
                                partWithName("room2").description("두번째 룸의 기본 이미지입니다.")
                        ),
                        responseFields(
                                fieldWithPath("houseId").description("생성된 하우스의 아이디입니다.")
                        )
                ));

        List<FileJpaEntity> fileInDB = fileJpaRepository.findAll();
        assertThat(fileInDB).hasSize(4);
        assertThat(fileInDB)
                .anySatisfy(fileJpaEntity -> assertThat(fileJpaEntity.getRealFileName()).isEqualTo("image.png"))
                .anySatisfy(fileJpaEntity -> assertThat(fileJpaEntity.getRealFileName()).isEqualTo("image2.png"))
                .anySatisfy(fileJpaEntity -> assertThat(fileJpaEntity.getRealFileName()).isEqualTo("image3.png"))
                .anySatisfy(fileJpaEntity -> assertThat(fileJpaEntity.getRealFileName()).isEqualTo("image4.png"));
    }
}