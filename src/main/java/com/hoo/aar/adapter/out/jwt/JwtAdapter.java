package com.hoo.aar.adapter.out.jwt;

import com.hoo.aar.adapter.in.web.authn.security.JwtProperties;
import com.hoo.aar.application.port.out.jwt.IssueAccessTokenPort;
import com.hoo.aar.application.port.out.persistence.user.BusinessUserInfo;
import com.hoo.admin.domain.user.snsaccount.SnsAccount;
import com.hoo.common.domain.Role;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class JwtAdapter implements IssueAccessTokenPort {

    private final MACSigner signer;
    private final JwtProperties jwtProperties;

    @Override
    public String issueAccessToken(SnsAccount snsAccount) {

        Long userId = snsAccount.isRegistered() ? snsAccount.getUserId().getId() : -1L;
        Role role = snsAccount.isRegistered() ? Role.USER : Role.TEMP_USER;

        return issueSnsAccessToken(snsAccount.getSnsAccountInfo().getNickname(), userId, snsAccount.getSnsAccountId().getPersistenceId(), role);
    }

    @Override
    public String issueAccessToken(BusinessUserInfo businessUserInfo) {
        return issueBusinessAccessToken(businessUserInfo.nickname(), businessUserInfo.userId(), businessUserInfo.businessUserId(), Role.USER);
    }

    private String issueBusinessAccessToken(String nickname, Long userId, Long businessUserId, Role role) {
        try {
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(nickname)
                    .issuer(jwtProperties.issuer())
                    .claim("userId", userId)
                    .claim("businessUserId", businessUserId)
                    .claim("role", role)
                    .expirationTime(new Date(System.currentTimeMillis() + jwtProperties.expire()))
                    .build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);
            return signedJWT.serialize();

        } catch (JOSEException e) {
            log.error("JWT Sign Error : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private String issueSnsAccessToken(String nickname, Long userId, Long snsId, Role role) {
        try {
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(nickname)
                    .issuer(jwtProperties.issuer())
                    .claim("userId", userId)
                    .claim("snsId", snsId)
                    .claim("role", role)
                    .expirationTime(new Date(System.currentTimeMillis() + jwtProperties.expire()))
                    .build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);
            return signedJWT.serialize();

        } catch (JOSEException e) {
            log.error("JWT Sign Error : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
