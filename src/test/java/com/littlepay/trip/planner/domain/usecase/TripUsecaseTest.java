package com.littlepay.trip.planner.domain.usecase;

import com.littlepay.trip.planner.domain.model.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static com.littlepay.trip.planner.domain.model.Stop.*;
import static com.littlepay.trip.planner.domain.model.TapType.OFF;
import static com.littlepay.trip.planner.domain.model.TapType.ON;
import static com.littlepay.trip.planner.domain.model.TripStatus.COMPLETED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class TripUsecaseTest {

    public static final String COMPANY_1 = "Company1";
    public static final String BUS_37 = "Bus37";
    public static final String PAN = "5500005555555559";

    static Stream<Arguments> calculateTrips() {
        return Stream.of(
                arguments(
                        List.of(
                                new Tap(1, LocalDateTime.of(2022, 1, 22, 13, 0), ON, STOP_1, COMPANY_1, BUS_37, PAN),
                                new Tap(2, LocalDateTime.of(2022, 1, 22, 13, 5), OFF, STOP_2, COMPANY_1, BUS_37, PAN)),
                        List.of(
                                new Trip(LocalDateTime.of(2022, 1, 22, 13, 0), LocalDateTime.of(2022, 1, 22, 13, 5), 900, STOP_1, STOP_2, 3.25, COMPANY_1, BUS_37, PAN, COMPLETED)
                        ))
        );
    }

    //    @MethodSource
//    @ParameterizedTest
    void calculateTrips(List<Tap> taps, List<Trip> expectedTrips) {

    }

    static Stream<Arguments> calculateAmount() {
        return Stream.of(
                arguments(STOP_1, STOP_2, 3.25),
                arguments(STOP_2, STOP_1, 3.25),
                arguments(STOP_2, STOP_3, 5.5),
                arguments(STOP_3, STOP_2, 5.5),
                arguments(STOP_1, STOP_3, 7.3),
                arguments(STOP_3, STOP_1, 7.3)
        );
    }

    @MethodSource
    @ParameterizedTest
    void calculateAmount(Stop from, Stop to, Double expected) {
        assertThat(TripUsecase.calculateHigherAmount(from, to)).isEqualTo(expected);
    }

    static Stream<Arguments> calculateHigherAmount() {
        return Stream.of(
                arguments(STOP_1, 7.3),
                arguments(STOP_2, 5.5),
                arguments(STOP_3, 7.3)
        );
    }

    @MethodSource
    @ParameterizedTest
    void calculateHigherAmount(Stop stop, Double expected) {
        assertThat(TripUsecase.calculateHigherAmount(stop)).isEqualTo(expected);
    }
}
