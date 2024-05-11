package com.trail_race.race_application.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
}
