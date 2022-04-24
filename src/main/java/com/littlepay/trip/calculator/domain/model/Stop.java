package com.littlepay.trip.calculator.domain.model;

public enum Stop {
    STOP_1,
    STOP_2,
    STOP_3;

    public static Stop from(String stopName) {
        return valueOf(stopName.replaceAll("(\\G(?!^)|\\b[a-zA-Z][a-z]*)([A-Z][a-z]*|\\d+)", "$1_$2").toUpperCase());
    }

    public String toString() {
        String snake_case = name().replaceAll("_([a-zA-Z\\d])", "$1").toLowerCase();
        return snake_case.substring(0, 1).toUpperCase() + snake_case.substring(1);
    }
}
