package com.hoo.admin.adapter.in.web.authn.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class AdminSecurityConfig {

    @Bean
    public SecurityFilterChain adminFilterChain(HttpSecurity http, AdminOAuth2UserService userService, AdminOAuth2SuccessHandler oAuth2SuccessHandler) throws Exception {
        return http
                .securityMatcher("/admin/**")
                .cors(cors -> cors.configurationSource(adminCorsConfigurationSource()))

                .formLogin(form -> form
                        .loginProcessingUrl("/admin/authn/login")
                        .defaultSuccessUrl("/login?form-success")
                        .failureUrl("/login?failure")
                )

                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(authorization ->
                                authorization.baseUri("/admin/authn/login/sns/**"))
                        .redirectionEndpoint(redirection ->
                                redirection.baseUri("/admin/authn/code/**"))
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(userService))
                        .successHandler(oAuth2SuccessHandler)
                        .failureUrl("/login?failure")
                )


                // 로컬 테스트 간 임시 허용
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 로컬 테스트 간 임시 허용

                .authorizeHttpRequests(authorize ->
                        authorize.anyRequest().permitAll()
                )

                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    protected CorsConfigurationSource adminCorsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOrigin("*"); // TODO 관리자로그인 완료 후 cors 재설정 필요
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setExposedHeaders((List.of("Authorization", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
