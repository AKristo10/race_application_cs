package com.trail_race.race_application.config;


import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.trail_race.race_application.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public JwtDecoder jwtDecoder(RsaKeyProperties rsaKeys) {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.getPublicKey()).build();
    }

    @Bean
    public JwtEncoder jwtEncoder(RsaKeyProperties rsaKeys) {
        JWK jwk = new RSAKey.Builder(rsaKeys.getPublicKey()).privateKey(rsaKeys.getPrivateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        return new JwtAuthenticationConverter();
    }

    @Bean
    @Primary
    public AuthenticationManager authenticationUserManager(PasswordEncoder passwordEncoder,
                                                           UserService userService) {
        DaoAuthenticationProvider userProvider = new DaoAuthenticationProvider();
        userProvider.setUserDetailsService(userService);
        userProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(userProvider);
    }

    @Bean
    @Order(1_00)
    public SecurityFilterChain publicApiSecurity(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(new AntPathRequestMatcher("/login"))
                .authorizeHttpRequests(configurer -> configurer
                        .anyRequest().permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    @Order(2_00)
    public SecurityFilterChain apiUserSecurity(HttpSecurity http, JwtDecoder jwtDecoder) throws Exception {
        return http
                .securityMatcher(new AntPathRequestMatcher("/**/user/**"))
                .authorizeHttpRequests(configurer -> configurer
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(configurer -> configurer.jwt(withDefaults()))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterAfter(new JwtSecurityFilter(jwtDecoder), BasicAuthenticationFilter.class)
                .build();
    }


}
