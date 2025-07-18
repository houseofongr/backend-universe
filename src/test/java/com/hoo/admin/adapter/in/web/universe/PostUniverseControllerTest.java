package com.hoo.admin.adapter.in.web.universe;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import com.hoo.common.adapter.out.persistence.entity.UniverseHashtagJpaEntity;
import com.hoo.common.adapter.out.persistence.entity.UniverseJpaEntity;
import com.hoo.common.adapter.out.persistence.repository.UniverseJpaRepository;
import com.hoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.hoo.file.adapter.out.persistence.repository.FileJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:/sql/universe.sql")
class PostUniverseControllerTest extends AbstractControllerTest {

    @Autowired
    FileJpaRepository fileJpaRepository;

    @Autowired
    UniverseJpaRepository universeJpaRepository;

    //language=JSON
    String metadata = """
            {
              "title": "우주",
              "description": "유니버스는 우주입니다.",
              "authorId": 1,
              "categoryId": 1,
              "publicStatus": "PUBLIC",
              "hashtags": [
                "우주", "행성", "지구", "별"
              ]
            }
            """;

    @Override
    public boolean useSpringSecurity() {
        return false;
    }

    @Test
    @DisplayName("유니버스 생성 API")
    void testCreateUniverse() throws Exception {

        MockPart metadataPart = new MockPart("metadata", metadata.getBytes());
        metadataPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        MockMultipartFile innerImage = new MockMultipartFile("innerImage", "universe_inner_image.png", "image/png", "image file".getBytes());
        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "universe_thumb.png", "image/png", "universe file".getBytes());
        MockMultipartFile thumbMusic = new MockMultipartFile("thumbMusic", "universe_music.mp3", "audio/mpeg", "music file".getBytes());

        mockMvc.perform(multipart("/admin/universes")
                        .file(thumbnail).file(thumbMusic).file(innerImage)
                        .part(metadataPart)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().is(201))
                .andDo(document("admin-universe-post",
                        requestParts(
                                partWithName("metadata").description("생성할 유니버스의 정보를 포함하는 Json 형태의 문자열입니다."),
                                partWithName("thumbnail").description("생성할 유니버스의 썸네일 이미지입니다."),
                                partWithName("thumbMusic").description("생성할 유니버스의 썸뮤직 오디오입니다."),
                                partWithName("innerImage").description("생성할 유니버스의 내부이미지입니다.")
                        ),
                        responseFields(
                                fieldWithPath("message").description("생성 완료 메시지 : '[#id]번 유니버스가 생성되었습니다.'"),
                                fieldWithPath("universeId").description("생성된 유니버스의 아이디입니다."),
                                fieldWithPath("thumbnailId").description("생성된 유니버스의 썸네일 파일 ID입니다."),
                                fieldWithPath("thumbMusicId").description("생성된 유니버스의 썸뮤직 파일 ID입니다."),
                                fieldWithPath("innerImageId").description("생성된 유니버스의 내부 이미지 파일 ID입니다."),
                                fieldWithPath("authorId").description("생성된 유니버스 작성자의 ID입니다."),
                                fieldWithPath("createdTime").description("생성된 유니버스의 유닉스 타임스탬프 형식의 생성(등록)일자입니다."),
                                fieldWithPath("title").description("생성된 유니버스의 제목입니다."),
                                fieldWithPath("author").description("생성된 유니버스의 작성자의 닉네임입니다."),
                                fieldWithPath("description").description("생성된 유니버스의 설명입니다."),
                                fieldWithPath("categoryId").description("생성된 유니버스의 카테고리 ID입니다."),
                                fieldWithPath("publicStatus").description("생성된 유니버스의 공개 여부입니다."),
                                fieldWithPath("hashtags").description("생성된 유니버스의 해시태그 리스트입니다.")
                        )
                ));

        UniverseJpaEntity newUniverse = universeJpaRepository.findAll().getLast();

        List<FileJpaEntity> fileInDB = fileJpaRepository.findAll();
        assertThat(fileInDB).hasSize(3);
        assertThat(fileInDB)
                .anySatisfy(fileJpaEntity -> assertThat(fileJpaEntity.getRealFileName()).isEqualTo("universe_music.mp3"))
                .anySatisfy(fileJpaEntity -> assertThat(fileJpaEntity.getRealFileName()).isEqualTo("universe_thumb.png"))
                .anySatisfy(fileJpaEntity -> assertThat(fileJpaEntity.getRealFileName()).isEqualTo("universe_inner_image.png"));

        FileJpaEntity thumbMusicFile = fileInDB.stream().filter(fileJpaEntity -> fileJpaEntity.getRealFileName().equals("universe_music.mp3")).findFirst().orElseThrow();
        FileJpaEntity thumbnailFile = fileInDB.stream().filter(fileJpaEntity -> fileJpaEntity.getRealFileName().equals("universe_thumb.png")).findFirst().orElseThrow();
        FileJpaEntity innerImageFile = fileInDB.stream().filter(fileJpaEntity -> fileJpaEntity.getRealFileName().equals("universe_inner_image.png")).findFirst().orElseThrow();

        assertThat(newUniverse.getThumbMusicFileId()).isEqualTo(thumbMusicFile.getId());
        assertThat(newUniverse.getThumbnailFileId()).isEqualTo(thumbnailFile.getId());
        assertThat(newUniverse.getInnerImageFileId()).isEqualTo(innerImageFile.getId());

        List<UniverseHashtagJpaEntity> universeHashtags = em.createQuery("select u from UniverseHashtagJpaEntity u", UniverseHashtagJpaEntity.class).getResultList();
        assertThat(universeHashtags.size()).isGreaterThan(4);
    }

}