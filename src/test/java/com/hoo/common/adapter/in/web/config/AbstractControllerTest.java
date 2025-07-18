package com.hoo.common.adapter.in.web.config;

import com.hoo.common.domain.Authority;
import com.hoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.hoo.file.adapter.out.persistence.repository.FileJpaRepository;
import com.hoo.file.application.service.FileProperties;
import com.hoo.file.domain.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@DocumentationTest
@Sql(value = "classpath:sql/clear.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public abstract class AbstractControllerTest {

    protected MockMvc mockMvc;

    @MockitoBean
    protected JavaMailSender javaMailSender;

    @TempDir
    protected Path tempDir;

    @Autowired
    protected FileJpaRepository fileJpaRepository;

    @Autowired
    protected FileProperties fileProperties;

    @Autowired
    protected EntityManager em;

    @Autowired
    protected RedisTemplate<String, String> redisTemplate;

    protected MockMvcTester mockMvcTester;

    protected String getBaseUrl() {
        return "api.archiveofongr.site";
    }

    protected boolean useSpringSecurity() {
        return true;
    }

    @BeforeEach
    protected void init(WebApplicationContext wac, RestDocumentationContextProvider restDocumentation) {
        DefaultMockMvcBuilder mockMvcBuilder = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .alwaysDo(log())
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(
                                modifyUris().scheme("https").host(getBaseUrl()).removePort(), prettyPrint())
                        .withResponseDefaults(prettyPrint())
                );

        mockMvc = useSpringSecurity() ? mockMvcBuilder.apply(springSecurity()).build() : mockMvcBuilder.build();

        ReflectionTestUtils.setField(fileProperties, "baseDir", tempDir.toString());

        this.mockMvcTester = MockMvcTester.from(wac);
    }

    protected void saveFile(File file) throws IOException {
        java.io.File tempFile = new java.io.File(file.getFileId().getPath());
        tempFile.mkdirs();
        tempFile.createNewFile();
        fileJpaRepository.save(FileJpaEntity.create(file));
    }

    /**
     * @Transactional nativeQuery를 통해 파일을 저장하기 때문에 해당 어노테이션 필수
     */
    protected void saveFile(Long fileId, FileType fileType) throws IOException {
        File file = getFile(fileId, fileType);
        java.io.File tempFile = new java.io.File(file.getFileId().getPath());
        tempFile.mkdirs();
        tempFile.createNewFile();

        saveFileByIdAndEntity(fileId, file);
    }

    private File getFile(Long fileId, FileType fileType) {
        return switch (fileType) {
            case IMAGE -> File.create(
                    FileId.create(
                            tempDir.toString(),
                            Authority.PUBLIC_FILE_ACCESS,
                            fileType,
                            String.format("test-%d.png", fileId),
                            UUID.randomUUID().toString() + ".png"),
                    FileStatus.CREATED,
                    OwnerId.empty(),
                    new FileSize(1234L, 100 * 1024 * 1024L));

            case AUDIO -> File.create(
                    FileId.create(
                            tempDir.toString(),
                            Authority.PUBLIC_FILE_ACCESS,
                            fileType,
                            String.format("test-%d.mp3", fileId),
                            UUID.randomUUID().toString() + ".mp3"),
                    FileStatus.CREATED,
                    OwnerId.empty(),
                    new FileSize(1234L, 100 * 1024 * 1024L));

            case VIDEO -> throw new UnsupportedOperationException();
        };
    }

    private void saveFileByIdAndEntity(Long fileId, File file) {
        em.createNativeQuery("""
                            INSERT INTO FILE (
                                ID, REAL_FILE_NAME, FILE_SYSTEM_NAME, ABSOLUTE_PATH,
                                IS_DELETED, FILE_SIZE, OWNER_ID, CREATED_TIME, UPDATED_TIME
                            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                        """)
                .setParameter(1, fileId)  // ID
                .setParameter(2, file.getFileId().getRealFileName())  // REAL_FILE_NAME
                .setParameter(3, file.getFileId().getFileSystemName())  // FILE_SYSTEM_NAME
                .setParameter(4, file.getFileId().getDirectory())  // ABSOLUTE_PATH
                .setParameter(5, false)  // IS_DELETED
                .setParameter(6, file.getSize().getFileByte())  // FILE_SIZE
                .setParameter(7, null)  // OWNER_ID
                .setParameter(8, Timestamp.valueOf("2025-07-09 15:00:00"))  // CREATED_TIME
                .setParameter(9, Timestamp.valueOf("2025-07-09 15:00:00"))  // UPDATED_TIME
                .executeUpdate();
    }

}
