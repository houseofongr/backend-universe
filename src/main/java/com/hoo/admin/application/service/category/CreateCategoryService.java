package com.hoo.admin.application.service.category;

import com.hoo.admin.application.port.in.category.CreateCategoryCommand;
import com.hoo.admin.application.port.in.category.CreateCategoryResult;
import com.hoo.admin.application.port.in.category.CreateCategoryUseCase;
import com.hoo.admin.application.port.out.category.SaveCategoryPort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateCategoryService implements CreateCategoryUseCase {

    private final SaveCategoryPort saveCategoryPort;

    @Override
    public CreateCategoryResult create(CreateCategoryCommand command) {
        if (command.kor() == null || command.kor().isBlank() || command.kor().length() > 100)
            throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        if (command.eng() == null || command.eng().isBlank() || command.eng().length() > 100)
            throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);

        return saveCategoryPort.save(command.kor(), command.eng());
    }
}
