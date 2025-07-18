package com.hoo.common.adapter.in.web.config;

import com.hoo.common.application.service.ErrorCode;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.payload.AbstractFieldsSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;

public class ErrorCodeResponseFieldsSnippet extends AbstractFieldsSnippet {

    public ErrorCodeResponseFieldsSnippet(String type, List<FieldDescriptor> descriptors, boolean ignoreUndocumentedFields) {
        super(type, descriptors, null, ignoreUndocumentedFields);
    }

    public static ErrorCodeResponseFieldsSnippet errorCodeResponseFields(
            String snippetFilePrefix, List<FieldDescriptor> fieldDescriptors) {
        return new ErrorCodeResponseFieldsSnippet(snippetFilePrefix, fieldDescriptors, true);
    }

    public static List<FieldDescriptor> errorCodeFieldDescriptors(ErrorCode[] enums) {
        List<FieldDescriptor> fieldDescriptors = new ArrayList<>();
        for (ErrorCode errorCode : enums) {
            FieldDescriptor attributes = fieldWithPath(errorCode.name())
                    .type(JsonFieldType.OBJECT)
                    .attributes(
                            key("code").value(errorCode.getCode()),
                            key("message").value(errorCode.getMessage()),
                            key("httpStatusCode").value(String.valueOf(errorCode.getStatus().value())),
                            key("httpStatusReason").value(errorCode.getStatus().getReasonPhrase()));
            fieldDescriptors.add(attributes);
        }
        return fieldDescriptors;
    }

    @Override
    public MediaType getContentType(Operation operation) {
        return operation.getResponse().getHeaders().getContentType();
    }

    @Override
    public byte[] getContent(Operation operation) throws IOException {
        return operation.getResponse().getContent();
    }
}
