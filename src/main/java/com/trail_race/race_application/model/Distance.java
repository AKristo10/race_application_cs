package com.trail_race.race_application.model;

import lombok.Getter;


@Getter
public enum Distance {
    K_5("5k"),
    K_10("10k"),
    HALF_MARATHON("HalfMarathon"),
    MARATHON("Marathon");

    private final String name;

    Distance(String name) {
        this.name = name;
    }

}
