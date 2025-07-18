package com.hoo.admin.adapter.in.web.universe;

import com.hoo.admin.application.port.in.universe.CreateUniverseCommand;
import com.hoo.admin.application.port.in.universe.CreateUniverseResult;
import com.hoo.admin.application.port.in.universe.CreateUniverseUseCase;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import static com.hoo.common.util.GsonUtil.gson;

@RestController
@RequiredArgsConstructor
public class PostUniverseController {

    private final CreateUniverseUseCase useCase;

    @PostMapping("/admin/universes")
    public ResponseEntity<CreateUniverseResult> create(@RequestParam String metadata, HttpServletRequest request) {

        if (request instanceof MultipartHttpServletRequest multipartRequest) {
            CreateUniverseCommand baseCommand = gson.fromJson(metadata, CreateUniverseCommand.class);
            CreateUniverseCommand fullCommand = CreateUniverseCommand.from(baseCommand, multipartRequest.getFileMap());
            return new ResponseEntity<>(useCase.create(fullCommand), HttpStatus.CREATED);
        }

        throw new AdminException(AdminErrorCode.INVALID_REQUEST_TYPE);
    }
}
