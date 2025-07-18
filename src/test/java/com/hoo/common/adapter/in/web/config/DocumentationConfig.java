package com.hoo.common.adapter.in.web.config;

import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.MockMvcOperationPreprocessorsConfigurer;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

public class DocumentationConfig {

    public static MockMvcOperationPreprocessorsConfigurer customDocumentation(RestDocumentationContextProvider restDocumentation) {
        return documentationConfiguration(restDocumentation)
                .operationPreprocessors()
                .withRequestDefaults(
                        modifyUris().scheme("https").host("api.archiveofongr.site").removePort(), prettyPrint())
                .withResponseDefaults(prettyPrint());
    }
}
