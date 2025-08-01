package com.hoo.universe.application;


import com.hoo.common.IssueIDPort;
import com.hoo.universe.api.in.dto.CreateCategoryCommand;
import com.hoo.universe.api.in.dto.CreateCategoryResult;
import com.hoo.universe.api.in.CreateCategoryUseCase;
import com.hoo.universe.api.out.UpdateCategoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateCategoryService implements CreateCategoryUseCase {

    private final IssueIDPort issueIDPort;
    private final UpdateCategoryPort updateCategoryPort;

    @Override
    public CreateCategoryResult createNewCategory(CreateCategoryCommand command) {
        UUID uuid = issueIDPort.issueNewID();
        updateCategoryPort.saveCategory(uuid, command.kor(), command.eng());
        return new CreateCategoryResult(
                uuid,
                command.eng(),
                command.kor()
        );
    }
}
