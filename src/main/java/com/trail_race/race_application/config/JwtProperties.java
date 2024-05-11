package com.trail_race.race_application.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {
    private Integer accessTokenExpirySeconds;
}
