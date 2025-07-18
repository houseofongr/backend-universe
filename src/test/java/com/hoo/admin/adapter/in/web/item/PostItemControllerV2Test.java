package com.hoo.admin.adapter.in.web.item;

import com.hoo.admin.application.port.in.item.CreateAndUpdateItemCommand;
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

class PostItemControllerV2Test extends AbstractControllerTest {


    @Test
    @Sql("PostItemControllerTestV2.sql")
    @DisplayName("아이템 생성 및 수정 API V2")
    void testCreateItemAPIV2() throws Exception {

        CreateAndUpdateItemCommand command = new CreateAndUpdateItemCommand(
                List.of(
                        new ItemData(null, "강아지", ItemType.CIRCLE, new ItemData.CircleData(200f, 200f, 10.5f), null, null),
                        new ItemData(null, "설이", ItemType.RECTANGLE, null, new ItemData.RectangleData(100f, 100f, 15f, 10f, 5f), null),
                        new ItemData(null, "화분", ItemType.ELLIPSE, null, null, new ItemData.EllipseData(500f, 500f, 15f, 10f, 90f))
                ),
                List.of(
                        new ItemData(1L, "강아지", ItemType.CIRCLE, new ItemData.CircleData(200f, 200f, 10.5f), null, null),
                        new ItemData(2L, "설이 수정", ItemType.RECTANGLE, null, new ItemData.RectangleData(100f, 100f, 15f, 10f, 5f), null),
                        new ItemData(3L, "화분 수정", ItemType.ELLIPSE, null, null, new ItemData.EllipseData(500f, 500f, 15f, 10f, 90f))
                )
        );

        mockMvc.perform(post("/admin/users/{userId}/homes/{homeId}/rooms/{roomId}/items/v2", 10L, 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(command)))
                .andExpect(status().is(201))
                .andDo(document("admin-item-post-v2",
                        pathParameters(
                                parameterWithName("userId").description("아이템을 소유할 사용자의 ID입니다."),
                                parameterWithName("homeId").description("아이템을 보유할 홈의 ID입니다."),
                                parameterWithName("roomId").description("아이템을 포함할 룸의 ID입니다.")
                        ),
                        requestFields(
                                fieldWithPath("createItems").description("생성할 아이템의 정보를 담은 배열입니다."),

                                fieldWithPath("createItems").description("생성할 아이템의 정보를 담은 배열입니다."),
                                fieldWithPath("createItems[].name").description("생성할 아이템의 이름입니다."),
                                fieldWithPath("createItems[].itemType").description("생성할 아이템의 타입입니다. +" + "\n" + "* 타입 : [CIRCLE, RECTANGLE, ELLIPSE]"),

                                fieldWithPath("createItems[].circleData").optional().description("원형 타입의 데이터입니다. +" + "\n" + "* 해당 타입으로 생성하지 않으면 비워서(null) 전송합니다."),
                                fieldWithPath("createItems[].rectangleData").optional().description("직사각형 타입의 데이터입니다. +" + "\n" + "* 해당 타입으로 생성하지 않으면 비워서(null) 전송합니다."),
                                fieldWithPath("createItems[].ellipseData").optional().description("타원형 타입의 데이터입니다. +" + "\n" + "* 해당 타입으로 생성하지 않으면 비워서(null) 전송합니다."),

                                fieldWithPath("createItems[].circleData.x").optional().description("원형의 x좌표입니다."),
                                fieldWithPath("createItems[].circleData.y").optional().description("원형의 y좌표입니다."),
                                fieldWithPath("createItems[].circleData.radius").optional().description("원형의 반지름입니다."),

                                fieldWithPath("createItems[].rectangleData.x").optional().description("직사각형의 x좌표입니다."),
                                fieldWithPath("createItems[].rectangleData.y").optional().description("직사각형의 y좌표입니다."),
                                fieldWithPath("createItems[].rectangleData.width").optional().description("직사각형의 가로 너비입니다."),
                                fieldWithPath("createItems[].rectangleData.height").optional().description("직사각형의 세로 높이입니다."),
                                fieldWithPath("createItems[].rectangleData.rotation").optional().description("직사각형의 회전 각도입니다."),

                                fieldWithPath("createItems[].ellipseData.x").optional().description("타원형의 x좌표입니다."),
                                fieldWithPath("createItems[].ellipseData.y").optional().description("타원형의 y좌표입니다."),
                                fieldWithPath("createItems[].ellipseData.radiusX").optional().description("타원형의 x축 반지름입니다."),
                                fieldWithPath("createItems[].ellipseData.radiusY").optional().description("타원형의 y축 반지름입니다."),
                                fieldWithPath("createItems[].ellipseData.rotation").optional().description("타원형의 회전 각도입니다."),

                                fieldWithPath("updateItems").description("수정할 아이템의 정보를 담은 배열입니다."),
                                fieldWithPath("updateItems[].id").description("수정할 아이템의 ID입니다."),
                                fieldWithPath("updateItems[].name").description("수정할 아이템의 이름입니다."),
                                fieldWithPath("updateItems[].itemType").description("수정할 아이템의 타입입니다. +" + "\n" + "* 타입 : [CIRCLE, RECTANGLE, ELLIPSE]"),

                                fieldWithPath("updateItems[].circleData").optional().description("원형 타입의 데이터입니다. +" + "\n" + "* 해당 타입으로 생성하지 않으면 비워서(null) 전송합니다."),
                                fieldWithPath("updateItems[].rectangleData").optional().description("직사각형 타입의 데이터입니다. +" + "\n" + "* 해당 타입으로 생성하지 않으면 비워서(null) 전송합니다."),
                                fieldWithPath("updateItems[].ellipseData").optional().description("타원형 타입의 데이터입니다. +" + "\n" + "* 해당 타입으로 생성하지 않으면 비워서(null) 전송합니다."),

                                fieldWithPath("updateItems[].circleData.x").optional().description("원형의 x좌표입니다."),
                                fieldWithPath("updateItems[].circleData.y").optional().description("원형의 y좌표입니다."),
                                fieldWithPath("updateItems[].circleData.radius").optional().description("원형의 반지름입니다."),

                                fieldWithPath("updateItems[].rectangleData.x").optional().description("직사각형의 x좌표입니다."),
                                fieldWithPath("updateItems[].rectangleData.y").optional().description("직사각형의 y좌표입니다."),
                                fieldWithPath("updateItems[].rectangleData.width").optional().description("직사각형의 가로 너비입니다."),
                                fieldWithPath("updateItems[].rectangleData.height").optional().description("직사각형의 세로 높이입니다."),
                                fieldWithPath("updateItems[].rectangleData.rotation").optional().description("직사각형의 회전 각도입니다."),

                                fieldWithPath("updateItems[].ellipseData.x").optional().description("타원형의 x좌표입니다."),
                                fieldWithPath("updateItems[].ellipseData.y").optional().description("타원형의 y좌표입니다."),
                                fieldWithPath("updateItems[].ellipseData.radiusX").optional().description("타원형의 x축 반지름입니다."),
                                fieldWithPath("updateItems[].ellipseData.radiusY").optional().description("타원형의 y축 반지름입니다."),
                                fieldWithPath("updateItems[].ellipseData.rotation").optional().description("타원형의 회전 각도입니다.")
                        ),
                        responseFields(
                                fieldWithPath("itemIds[]").description("생성 및 수정된 아이템의 ID 리스트입니다.")
                        )
                ));
    }
}