package com.hoo.admin.adapter.in.web.item;

import com.hoo.common.adapter.in.web.config.AbstractControllerTest;
import com.hoo.common.adapter.out.persistence.entity.ItemShapeRectangleJpaEntity;
import com.hoo.common.adapter.out.persistence.repository.ItemJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PatchItemControllerTest extends AbstractControllerTest {

    @Autowired
    ItemJpaRepository itemJpaRepository;

    @Test
    @DisplayName("아이템 수정 시 데이터 없을 경우 예외처리")
    void testNoDataExceptionHandle() throws Exception {
        mockMvc.perform(patch("/admin/items/{itemId}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"updateData\":null}"))
                .andExpect(status().is(400));
    }

    @Test
    @Sql("PatchItemControllerTest.sql")
    @DisplayName("아이템 수정 API")
    void testPatchItemAPI() throws Exception {

        //language=JSON
        String content = """
                {
                  "updateData": {
                    "name" : "고양이",
                    "itemType" : "RECTANGLE",
                    "rectangleData" : {
                      "x" : 300,
                      "y" : 200,
                      "width" : 20,
                      "height" : 20,
                      "rotation" : 10
                    }
                  }
                }
                """;

        mockMvc.perform(patch("/admin/items/{itemId}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().is(200))
                .andDo(document("admin-item-patch",
                        pathParameters(
                                parameterWithName("itemId").description("수정할 아이템의 ID입니다.")
                        ),
                        requestFields(
                                fieldWithPath("updateData.name").optional().description("수정할 아이템의 이름입니다."),
                                fieldWithPath("updateData.itemType").optional().description("수정할 아이템의 타입입니다. +" + "\n" + "* 타입 : [CIRCLE, RECTANGLE, ELLIPSE]"),

                                fieldWithPath("updateData.circleData").optional().description("원형 타입의 데이터입니다. +" + "\n" + "* 해당 타입으로 수정하지 않으면 비워서(null) 전송합니다.").type("Object"),
                                fieldWithPath("updateData.rectangleData").optional().description("직사각형 타입의 데이터입니다. +" + "\n" + "* 해당 타입으로 수정하지 않으면 비워서(null) 전송합니다."),
                                fieldWithPath("updateData.ellipseData").optional().description("타원형 타입의 데이터입니다. +" + "\n" + "* 해당 타입으로 수정하지 않으면 비워서(null) 전송합니다.").type("Object"),

                                fieldWithPath("updateData.circleData.x").optional().description("원형의 x좌표입니다.").type("Number"),
                                fieldWithPath("updateData.circleData.y").optional().description("원형의 y좌표입니다.").type("Number"),
                                fieldWithPath("updateData.circleData.radius").optional().description("원형의 반지름입니다.").type("Number"),

                                fieldWithPath("updateData.rectangleData.x").optional().description("직사각형의 x좌표입니다."),
                                fieldWithPath("updateData.rectangleData.y").optional().description("직사각형의 y좌표입니다."),
                                fieldWithPath("updateData.rectangleData.width").optional().description("직사각형의 가로 너비입니다."),
                                fieldWithPath("updateData.rectangleData.height").optional().description("직사각형의 세로 높이입니다."),
                                fieldWithPath("updateData.rectangleData.rotation").optional().description("직사각형의 회전 각도입니다."),

                                fieldWithPath("updateData.ellipseData.x").optional().description("타원형의 x좌표입니다.").type("Number"),
                                fieldWithPath("updateData.ellipseData.y").optional().description("타원형의 y좌표입니다.").type("Number"),
                                fieldWithPath("updateData.ellipseData.radiusX").optional().description("타원형의 x축 반지름입니다.").type("Number"),
                                fieldWithPath("updateData.ellipseData.radiusY").optional().description("타원형의 y축 반지름입니다.").type("Number"),
                                fieldWithPath("updateData.ellipseData.rotation").optional().description("타원형의 회전 각도입니다.").type("Number")
                        ),
                        responseFields(
                                fieldWithPath("message").description("수정 완료 메시지 : 0번 아이템의 정보가 수정되었습니다.")
                        )
                ));

        assertThat(itemJpaRepository.findById(2L).get().getName()).isEqualTo("고양이");
        assertThat(itemJpaRepository.findById(2L).get().getShape()).isInstanceOf(ItemShapeRectangleJpaEntity.class);
    }
}