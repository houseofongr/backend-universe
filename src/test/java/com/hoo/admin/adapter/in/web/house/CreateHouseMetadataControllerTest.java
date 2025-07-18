package com.hoo.admin.adapter.in.web.house;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class CreateHouseMetadataControllerTest extends AbstractControllerTest {


    @Test
    @DisplayName("Metadata 생성 테스트")
    void testCreateMetadata() throws Exception {
        mockMvc.perform(get("/mock/admin/create-house-metadata")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(document("admin-create-house-metadata",
                        responseFields(
                                fieldWithPath("house.title").description("생성할 하우스의 제목입니다."),
                                fieldWithPath("house.owner").description("생성할 하우스의 작가입니다."),
                                fieldWithPath("house.description").description("생성할 하우스에 대한 설명입니다."),
                                fieldWithPath("house.houseForm").description("기본 이미지로 사용할 이미지 파일의 Form 태그 name 속성값입니다. +" + "\n" + "* 지원 파일형식 : [jpg, jpeg, png, svg, gif]"),
                                fieldWithPath("house.borderForm").description("테두리 이미지로 사용할 이미지 파일의 Form 태그 name 속성값입니다. +" + "\n" + "* 지원 파일형식 : [jpg, jpeg, png, svg, gif]"),
                                fieldWithPath("house.width").description("하우스의 가로 길이입니다."),
                                fieldWithPath("house.height").description("하우스의 높이입니다."),
                                fieldWithPath("rooms[].form").description("룸의 이미지로 사용할 이미지 파일의 Form 태그 name 속성값입니다. +" + "\n" + "* 지원 파일형식 : [jpg, jpeg, png, svg, gif]"),
                                fieldWithPath("rooms[].name").description("룸의 이름입니다."),
                                fieldWithPath("rooms[].x").description("룸의 시작점(X좌표)입니다."),
                                fieldWithPath("rooms[].y").description("룸의 시작점(Y좌표)입니다."),
                                fieldWithPath("rooms[].z").description("룸의 시작점(Z좌표)입니다."),
                                fieldWithPath("rooms[].width").description("룸의 가로 길이입니다."),
                                fieldWithPath("rooms[].height").description("룸의 높이입니다.")
                        )));
    }

}
