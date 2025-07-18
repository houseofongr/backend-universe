package com.hoo.aar.application.service.user;

import com.hoo.aar.application.port.in.user.DeleteMyInfoUseCase;
import com.hoo.aar.application.port.out.api.UnlinkKakaoLoginPort;
import com.hoo.admin.application.port.in.user.DeleteUserCommand;
import com.hoo.admin.application.port.in.user.DeleteUserUseCase;
import com.hoo.common.application.port.in.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteMyInfoService implements DeleteMyInfoUseCase {

    private final UnlinkKakaoLoginPort unlinkKakaoLoginPort;
    private final DeleteUserUseCase deleteUserUseCase;

    @Override
    public MessageDto deleteMyInfo(Long userId, DeleteUserCommand command) {
        unlinkKakaoLoginPort.unlink(userId);
        return deleteUserUseCase.deleteUser(userId, command);
    }
}
