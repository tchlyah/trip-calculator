package com.littlepay.trip.planner.domain.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class TupleTest {

    static Stream<Arguments> tuples() {
        return Stream.of(
                arguments(new Tuple<>("a", "b"), new Tuple<>("b", "a")),
                arguments(new Tuple<>(1, 2), new Tuple<>(2, 1)),
                arguments(new Tuple<>(null, "a"), new Tuple<>("a", null)),
                arguments(new Tuple<>(null, null), new Tuple<>(null, null))
        );
    }

    @ParameterizedTest
    @MethodSource("tuples")
    <T> void testEquals(Tuple<T, String> t1, Tuple<T, String> t2) {
        assertThat(t1).isEqualTo(t2);
    }

    @ParameterizedTest
    @MethodSource("tuples")
    <T> void testHashCode(Tuple<T, String> t1, Tuple<T, String> t2) {
        assertThat(t1.hashCode()).isEqualTo(t2.hashCode());
    }
}
