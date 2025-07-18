package com.hoo.file.adapter.in.web.privates;

import com.hoo.aar.adapter.out.jwt.JwtAdapter;
import com.hoo.admin.domain.user.snsaccount.SnsAccount;
import com.hoo.admin.domain.user.snsaccount.SnsDomain;
import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import com.hoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.hoo.file.adapter.out.persistence.repository.FileJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.nio.file.Files;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetPrivateImageControllerV2Test extends AbstractControllerTest {

    @Autowired
    FileJpaRepository fileJpaRepository;

    @Autowired
    JwtAdapter jwtAdapter;

    @Override
    protected String getBaseUrl() {
        return "file.archiveofongr.site";
    }

    @Override
    protected boolean useSpringSecurity() {
        return false;
    }

    @Test
    @DisplayName("이미지파일 다운로드 API")
    void testFile() throws Exception {

        ClassPathResource resource = new ClassPathResource("private/images/secret.jpeg");
        byte[] bytes = Files.readAllBytes(resource.getFile().toPath());
        FileJpaEntity entity = new FileJpaEntity(null, "secret.jpeg", "secret.jpeg", resource.getFile().getParent(), false, (long) bytes.length, null);
        fileJpaRepository.save(entity);

        String accessToken = jwtAdapter.issueAccessToken(SnsAccount.load(1L, SnsDomain.KAKAO, "test", "남상엽", "leaf", "test@example.com", null, null, 1L));

        mockMvc.perform(get("/private/images/v2/{imageId}", entity.getId())
                        .param("accessToken", accessToken))
                .andExpect(status().is(200))
                .andDo(document("file-private-images-download-v2",
                        pathParameters(parameterWithName("imageId").description("조회(다운로드)할 이미지 ID입니다.")),
                        queryParameters(
                                parameterWithName("accessToken").description("권한을 확인하기 위한 토큰입니다. +" + "\n" + "* 로그인 시 발급됩니다.")
                        )
                ));
    }
}