package com.littlepay.trip.planner.domain.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.littlepay.trip.planner.domain.model.Stop.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class StopTest {

    static Stream<Arguments> stops() {
        return Stream.of(
                arguments("Stop1", STOP_1),
                arguments("Stop2", STOP_2),
                arguments("Stop3", STOP_3)
        );
    }

    @ParameterizedTest
    @MethodSource("stops")
    void testFrom(String name, Stop expected) {
        assertThat(Stop.from(name)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("stops")
    void testToString(String expected, Stop name) {
        assertThat(name.toString()).isEqualTo(expected);
    }
}
