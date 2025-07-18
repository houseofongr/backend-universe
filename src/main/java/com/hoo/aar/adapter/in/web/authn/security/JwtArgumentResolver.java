package com.hoo.aar.adapter.in.web.authn.security;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class JwtArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Jwt.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Jwt jwtAnnotation = parameter.getParameterAnnotation(Jwt.class);
        String claimName = jwtAnnotation.value();
        boolean required = jwtAnnotation.required();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof org.springframework.security.oauth2.jwt.Jwt jwt)) {
            if (required) {
                throw new IllegalArgumentException("JWT not found or invalid");
            } else {
                return null;
            }
        }

        Object claimValue = jwt.getClaim(claimName);
        if (claimValue == null && required) {
            throw new IllegalArgumentException("Required JWT claim '" + claimName + "' not found");
        }

        return claimValue;    }
}
