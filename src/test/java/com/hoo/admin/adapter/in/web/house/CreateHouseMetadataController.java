package com.hoo.admin.adapter.in.web.house;

import com.hoo.admin.application.port.in.house.CreateHouseMetadata;
import com.hoo.admin.application.port.in.house.CreateHouseMetadataTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateHouseMetadataController {

    @GetMapping("/mock/admin/create-house-metadata")
    ResponseEntity<CreateHouseMetadata> getMetadata() {
        return new ResponseEntity<>(CreateHouseMetadataTest.getCreateHouseMetadata(), HttpStatus.OK);
    }

}
