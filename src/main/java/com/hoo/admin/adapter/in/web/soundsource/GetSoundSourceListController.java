package com.hoo.admin.adapter.in.web.soundsource;

import com.hoo.admin.application.port.in.soundsource.QuerySoundSourceListCommand;
import com.hoo.admin.application.port.in.soundsource.QuerySoundSourceListResult;
import com.hoo.admin.application.port.in.soundsource.QuerySoundSourceListUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetSoundSourceListController {

    private final QuerySoundSourceListUseCase useCase;

    @GetMapping("/admin/sound-sources")
    public ResponseEntity<QuerySoundSourceListResult> getSoundSourceList(Pageable pageable) {
        return ResponseEntity.ok(useCase.querySoundSourceList(new QuerySoundSourceListCommand(pageable)));
    }
}
