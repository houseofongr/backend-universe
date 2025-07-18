package com.hoo.common.adapter.in.web;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class ControllerLogAspect {

    @Around("within(com.hoo..*Controller)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {

        RequestAttributes requestAttribute = RequestContextHolder.getRequestAttributes();

        if (requestAttribute != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            log.info("[API REQUEST]");
            log.info(" - CONTROLLER : {}({})", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            log.info(" - REQUEST URL : {}]", request.getRequestURL());
            log.info(" - PARAMETERS : {}", paramMapToString(request.getParameterMap()));
            log.info(" - REQUEST BODY : {}", requestBodyToString(request.getInputStream()));
            log.info(" - AUTHENTICATION : {}", request.getHeader(HttpHeaders.AUTHORIZATION));
            log.info("[API REQUEST]");
        }

        long startAt = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        if (result != null) {
            log.info("-----------------------");
            log.info("[API RESPONSE]");
            log.info(" - RESPONSE BODY : {}", omit(result.toString()));
            log.info(" - RESPONSE TIME : {}ms", System.currentTimeMillis() - startAt);
            log.info("[API RESPONSE]");
        }

        return result;
    }

    private String requestBodyToString(ServletInputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        return br.readLine();
    }

    private String paramMapToString(Map<String, String[]> paramMap) {
        if (paramMap.isEmpty()) return null;
        return paramMap.entrySet().stream()
                .map(entry -> String.format("%s -> (%s)",
                        entry.getKey(), join(entry.getValue())))
                .collect(Collectors.joining(", "));
    }

    private String join(String[] s) {
        return omit(Arrays.toString(s));
    }

    private String omit(String s) {
        return s.length() > 300 ? s.substring(0, 300) + "..." : s;
    }
}
