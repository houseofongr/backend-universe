package com.hoo.file.adapter.in.web.publics;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostPublicImageControllerTest extends AbstractControllerTest {

    @Override
    protected String getBaseUrl() {
        return "file.archiveofongr.site";
    }

    @Test
    @DisplayName("이미지파일 업로드 API")
    void testFile() throws Exception {
        MockMultipartFile image = new MockMultipartFile("images", "image.png", "image/png", "<<png data 1>>".getBytes());
        MockMultipartFile image2 = new MockMultipartFile("images", "image2.png", "image/png", "<<png data 2>>".getBytes());

        mockMvc.perform(multipart("/public/images")
                        .file(image).file(image2)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))
                .andDo(document("file-public-images-upload",
                        responseFields(
                                fieldWithPath("fileInfos[].id").description("등록된 파일의 아이디입니다."),
                                fieldWithPath("fileInfos[].ownerId").description("등록된 파일의 소유자 ID입니다.(공개 이미지이므로 소유자는 없습니다.)"),
                                fieldWithPath("fileInfos[].fileSystemName").description("저장된 파일명입니다."),
                                fieldWithPath("fileInfos[].realName").description("실제 파일명입니다."),
                                fieldWithPath("fileInfos[].size").description("등록된 파일의 용량입니다."),
                                fieldWithPath("fileInfos[].authority").description("등록된 파일의 접근 권한입니다. +" + "\n" + " - PUBLIC_FILE_ACCESS : 공개된 파일 +" + "\n" + " - PRIVATE_FILE_ACCESS : 비공개 파일(권한이 있는 사용자만 접근 가능)")
                        )));
    }
}
