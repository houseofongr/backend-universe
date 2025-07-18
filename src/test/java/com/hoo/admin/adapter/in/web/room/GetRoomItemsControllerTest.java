package com.hoo.admin.adapter.in.web.room;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetRoomItemsControllerTest extends AbstractControllerTest {


    @Test
    @Sql("GetRoomItemsControllerTest.sql")
    @DisplayName("룸 및 아이템 조회 API")
    void testGetRoomAndItemsAPI() throws Exception {
        mockMvc.perform(get("/admin/homes/{homeId}/rooms/{roomId}/items", 5L, 1L))
                .andExpect(status().is(200))
                .andDo(document("admin-room-get-items",
                        pathParameters(
                                parameterWithName("homeId").description("조회할 홈의 ID입니다."),
                                parameterWithName("roomId").description("조회할 룸의 ID입니다.")
                        ),
                        responseFields(
                                fieldWithPath("room.name").description("룸의 이름입니다."),
                                fieldWithPath("room.width").description("룸의 가로 길이입니다."),
                                fieldWithPath("room.height").description("룸의 높이입니다."),
                                fieldWithPath("room.imageId").description("룸의 이미지 ID입니다."),

                                fieldWithPath("items[].id").description("아이템의 ID입니다."),
                                fieldWithPath("items[].name").description("아이템의 이름입니다."),
                                fieldWithPath("items[].itemType").description("아이템의 타입입니다. +" + "\n" + "* 타입 : [CIRCLE, RECTANGLE, ELLIPSE]"),

                                fieldWithPath("items[].circleData").optional().description("원형 타입의 데이터입니다.").type("Object"),
                                fieldWithPath("items[].rectangleData").optional().description("직사각형 타입의 데이터입니다."),
                                fieldWithPath("items[].ellipseData").optional().description("타원형 타입의 데이터입니다.").type("Object"),

                                fieldWithPath("items[].circleData.x").optional().description("원형의 x좌표입니다.").type("Number"),
                                fieldWithPath("items[].circleData.y").optional().description("원형의 y좌표입니다.").type("Number"),
                                fieldWithPath("items[].circleData.radius").optional().description("원형의 반지름입니다.").type("Number"),

                                fieldWithPath("items[].rectangleData.x").optional().description("직사각형의 x좌표입니다."),
                                fieldWithPath("items[].rectangleData.y").optional().description("직사각형의 y좌표입니다."),
                                fieldWithPath("items[].rectangleData.width").optional().description("직사각형의 가로 너비입니다."),
                                fieldWithPath("items[].rectangleData.height").optional().description("직사각형의 세로 높이입니다."),
                                fieldWithPath("items[].rectangleData.rotation").optional().description("직사각형의 회전 각도입니다."),

                                fieldWithPath("items[].ellipseData.x").optional().description("타원형의 x좌표입니다.").type("Number"),
                                fieldWithPath("items[].ellipseData.y").optional().description("타원형의 y좌표입니다.").type("Number"),
                                fieldWithPath("items[].ellipseData.radiusX").optional().description("타원형의 x축 반지름입니다.").type("Number"),
                                fieldWithPath("items[].ellipseData.radiusY").optional().description("타원형의 y축 반지름입니다.").type("Number"),
                                fieldWithPath("items[].ellipseData.rotation").optional().description("타원형의 회전 각도입니다.").type("Number")
                        )
                ));
    }
}