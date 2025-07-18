package com.hoo.admin.application.service.user;

import com.hoo.admin.application.port.in.user.*;
import com.hoo.admin.application.port.out.user.FindUserPort;
import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import com.hoo.admin.domain.user.DeletedUser;
import com.hoo.admin.domain.user.User;
import com.hoo.common.application.port.in.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteUserService implements DeleteUserUseCase {

    private final FindUserPort findUserPort;
    private final CreateDeletedUserPort createDeletedUserPort;
    private final SaveDeletedUserPort saveDeletedUserPort;
    private final DeleteUserPort deleteUserPort;

    @Override
    public MessageDto deleteUser(Long userId, DeleteUserCommand command) {
        User user = findUserPort.loadUser(userId)
                .orElseThrow(() -> new AdminException(AdminErrorCode.USER_NOT_FOUND));

        DeletedUser deletedUser = createDeletedUserPort.createDeletedUser(user, command.termsOfDeletionAgreement(), command.personalInformationDeletionAgreement());

        saveDeletedUserPort.saveDeletedUser(deletedUser);

        deleteUserPort.deleteUser(user);

        return new MessageDto("회원탈퇴가 완료되었습니다.");
    }
}
