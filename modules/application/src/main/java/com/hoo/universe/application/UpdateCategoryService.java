package com.hoo.universe.application;

import com.hoo.universe.api.in.web.dto.command.UpdateCategoryCommand;
import com.hoo.universe.api.in.web.dto.result.UpdateCategoryResult;
import com.hoo.universe.api.in.web.usecase.UpdateCategoryUseCase;
import com.hoo.universe.api.out.persistence.CommandCategoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateCategoryService implements UpdateCategoryUseCase {

    private final CommandCategoryPort commandCategoryPort;

    @Override
    public UpdateCategoryResult updateCategory(UUID categoryID, UpdateCategoryCommand command) {

        commandCategoryPort.updateCategory(categoryID, command.kor(), command.eng());

        return new UpdateCategoryResult(
                categoryID,
                command.eng(),
                command.kor()
        );
    }
}
