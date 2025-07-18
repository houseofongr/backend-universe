package com.hoo.admin.adapter.in.web.soundsource;

import com.hoo.admin.application.port.in.soundsource.CreateSoundSourceMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.hoo.common.util.GsonUtil.gson;

@RestController
public class CreateSoundSourceMetadataController {

    @GetMapping("/mock/admin/create-soundsource-metadata")
    ResponseEntity<CreateSoundSourceMetadata> getMetadata() {

        //language=JSON
        String data = """
                {
                  "name" : "골골송",
                  "description" : "2025년 설이가 보내는 골골송",
                  "isActive" : true
                }
                """;

        return new ResponseEntity<>(gson.fromJson(data, CreateSoundSourceMetadata.class), HttpStatus.OK);
    }

}
