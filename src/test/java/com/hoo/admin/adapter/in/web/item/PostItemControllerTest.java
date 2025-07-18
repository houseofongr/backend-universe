package com.hoo.admin.adapter.in.web.item;

import com.hoo.admin.application.port.in.item.CreateItemCommand;
import com.hoo.admin.application.port.in.item.ItemData;
import com.hoo.admin.domain.item.ItemType;
import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.hoo.common.util.GsonUtil.gson;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostItemControllerTest extends AbstractControllerTest {

    @Test
    @DisplayName("아이템 생성 시 데이터 없으면 오류")
    void testNoDataExceptionHandler() throws Exception {
        mockMvc.perform(post("/admin/users/{userId}/homes/{homeId}/rooms/{roomId}/items", 10L, 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"items\": []}"))
                .andExpect(status().is(400));
    }

    @Test
    @Sql("PostItemControllerTest.sql")
    @DisplayName("아이템 생성 API")
    void testCreateItemAPI() throws Exception {

        CreateItemCommand command = new CreateItemCommand(
                List.of(
                        new ItemData(null, "강아지", ItemType.CIRCLE, new ItemData.CircleData(200f, 200f, 10.5f), null, null),
                        new ItemData(null, "설이", ItemType.RECTANGLE, null, new ItemData.RectangleData(100f, 100f, 15f, 10f, 5f), null),
                        new ItemData(null, "화분", ItemType.ELLIPSE, null, null, new ItemData.EllipseData(500f, 500f, 15f, 10f, 90f))
                )
        );

        mockMvc.perform(post("/admin/users/{userId}/homes/{homeId}/rooms/{roomId}/items", 10L, 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(command)))
                .andExpect(status().is(201))
                .andDo(document("admin-item-post",
                        pathParameters(
                                parameterWithName("userId").description("생성할 아이템을 소유할 사용자의 ID입니다."),
                                parameterWithName("homeId").description("생성할 아이템을 보유할 홈의 ID입니다."),
                                parameterWithName("roomId").description("생성할 아이템을 포함할 룸의 ID입니다.")
                        ),
                        requestFields(
                                fieldWithPath("items").description("생성할 아이템의 정보를 담은 배열입니다."),
                                fieldWithPath("items[].name").description("생성할 아이템의 이름입니다."),
                                fieldWithPath("items[].itemType").description("생성할 아이템의 타입입니다. +" + "\n" + "* 타입 : [CIRCLE, RECTANGLE, ELLIPSE]"),

                                fieldWithPath("items[].circleData").optional().description("원형 타입의 데이터입니다. +" + "\n" + "* 해당 타입으로 생성하지 않으면 비워서(null) 전송합니다."),
                                fieldWithPath("items[].rectangleData").optional().description("직사각형 타입의 데이터입니다. +" + "\n" + "* 해당 타입으로 생성하지 않으면 비워서(null) 전송합니다."),
                                fieldWithPath("items[].ellipseData").optional().description("타원형 타입의 데이터입니다. +" + "\n" + "* 해당 타입으로 생성하지 않으면 비워서(null) 전송합니다."),

                                fieldWithPath("items[].circleData.x").optional().description("원형의 x좌표입니다."),
                                fieldWithPath("items[].circleData.y").optional().description("원형의 y좌표입니다."),
                                fieldWithPath("items[].circleData.radius").optional().description("원형의 반지름입니다."),

                                fieldWithPath("items[].rectangleData.x").optional().description("직사각형의 x좌표입니다."),
                                fieldWithPath("items[].rectangleData.y").optional().description("직사각형의 y좌표입니다."),
                                fieldWithPath("items[].rectangleData.width").optional().description("직사각형의 가로 너비입니다."),
                                fieldWithPath("items[].rectangleData.height").optional().description("직사각형의 세로 높이입니다."),
                                fieldWithPath("items[].rectangleData.rotation").optional().description("직사각형의 회전 각도입니다."),

                                fieldWithPath("items[].ellipseData.x").optional().description("타원형의 x좌표입니다."),
                                fieldWithPath("items[].ellipseData.y").optional().description("타원형의 y좌표입니다."),
                                fieldWithPath("items[].ellipseData.radiusX").optional().description("타원형의 x축 반지름입니다."),
                                fieldWithPath("items[].ellipseData.radiusY").optional().description("타원형의 y축 반지름입니다."),
                                fieldWithPath("items[].ellipseData.rotation").optional().description("타원형의 회전 각도입니다.")
                        ),
                        responseFields(
                                fieldWithPath("createdItemIds[]").description("생성된 아이템의 ID 리스트입니다.")
                        )
                ));
    }
}