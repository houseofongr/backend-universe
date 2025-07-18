package com.hoo.aar.adapter.out.api;

import com.hoo.common.adapter.out.persistence.repository.SnsAccountJpaRepository;
import com.hoo.aar.application.port.out.api.UnlinkKakaoLoginPort;
import com.hoo.admin.domain.user.snsaccount.SnsDomain;
import com.hoo.common.adapter.out.persistence.entity.SnsAccountJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoLoginApiAdapter implements UnlinkKakaoLoginPort {

    private final SnsAccountJpaRepository snsAccountJpaRepository;
    private final KakaoLoginApi kakaoLoginApi;

    @Override
    public void unlink(Long userId) {
        for (SnsAccountJpaEntity entity : snsAccountJpaRepository.findAllByUserId(userId)) {
            if (entity.getSnsDomain().equals(SnsDomain.KAKAO)) {
                kakaoLoginApi.unlink(entity.getSnsId());
                return;
            }
        }
    }
}
