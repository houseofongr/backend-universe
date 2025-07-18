package com.hoo.admin.application.service.category;

import com.hoo.admin.application.port.in.category.UpdateCategoryCommand;
import com.hoo.admin.application.port.in.category.UpdateCategoryResult;
import com.hoo.admin.application.port.in.category.UpdateCategoryUseCase;
import com.hoo.admin.application.port.out.category.UpdateCategoryPort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateCategoryService implements UpdateCategoryUseCase {

    private final UpdateCategoryPort updateCategoryPort;

    @Override
    public UpdateCategoryResult update(Long categoryId, UpdateCategoryCommand command) {
        if (command.kor() == null || command.kor().isBlank() || command.kor().length() > 100)
            throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        if (command.eng() == null || command.eng().isBlank() || command.eng().length() > 100)
            throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);

        return updateCategoryPort.update(categoryId, command.kor(), command.eng());
    }
}
