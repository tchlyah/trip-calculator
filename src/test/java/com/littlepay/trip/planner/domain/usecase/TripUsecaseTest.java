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
import static com.littlepay.trip.planner.domain.model.TripStatus.*;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class TripUsecaseTest {

    static final String COMPANY_1 = "Company1";
    static final String COMPANY_2 = "Company2";
    static final String BUS_37 = "Bus37";
    static final String BUS_47 = "Bus47";
    static final String BUS_19 = "Bus19";
    static final String PAN_1 = "5500005555555559";
    static final String PAN_2 = "122000000000003";
    public static final LocalDateTime DATE_1 = LocalDateTime.of(2022, 1, 22, 13, 0);

    static Stream<Arguments> calculateTrips() {
        return Stream.of(
                arguments(
                        "COMPLETED",
                        List.of(
                                new Tap(1, DATE_1, ON, STOP_1, COMPANY_1, BUS_37, PAN_1),
                                new Tap(2, DATE_1.plus(5, MINUTES), OFF, STOP_2, COMPANY_1, BUS_37, PAN_1)),
                        List.of(
                                new Trip(DATE_1, DATE_1.plus(5, MINUTES), 300, STOP_1, STOP_2, 3.25, COMPANY_1, BUS_37, PAN_1, COMPLETED)
                        )),
                arguments(
                        "INCOMPLETE",
                        List.of(
                                new Tap(1, DATE_1, ON, STOP_1, COMPANY_1, BUS_37, PAN_1)),
                        List.of(
                                new Trip(DATE_1, null, 0, STOP_1, null, 7.3, COMPANY_1, BUS_37, PAN_1, INCOMPLETE)
                        )),
                arguments(
                        "CANCELED",
                        List.of(
                                new Tap(1, DATE_1, ON, STOP_1, COMPANY_1, BUS_37, PAN_1),
                                new Tap(2, DATE_1.plus(5, MINUTES), OFF, STOP_1, COMPANY_1, BUS_37, PAN_1)),
                        List.of(
                                new Trip(DATE_1, DATE_1.plus(5, MINUTES), 300, STOP_1, STOP_1, 0, COMPANY_1, BUS_37, PAN_1, CANCELLED)
                        )),
                arguments(
                        "CANCELED, Different pans",
                        List.of(
                                new Tap(1, DATE_1, ON, STOP_1, COMPANY_1, BUS_37, PAN_1),
                                new Tap(2, DATE_1.plus(5, MINUTES), ON, STOP_2, COMPANY_1, BUS_37, PAN_2)),
                        List.of(
                                new Trip(DATE_1, null, 0, STOP_1, null, 7.3, COMPANY_1, BUS_37, PAN_1, INCOMPLETE),
                                new Trip(DATE_1.plus(5, MINUTES), null, 0, STOP_2, null, 5.5, COMPANY_1, BUS_37, PAN_2, INCOMPLETE)
                        )),
                arguments("Multiple trips, same pans",
                        List.of(
                                new Tap(1, DATE_1, ON, STOP_1, COMPANY_1, BUS_37, PAN_1),
                                new Tap(2, DATE_1.plus(5, MINUTES), OFF, STOP_2, COMPANY_1, BUS_37, PAN_1),
                                new Tap(3, DATE_1.plus(10, MINUTES), ON, STOP_2, COMPANY_1, BUS_47, PAN_1),
                                new Tap(4, DATE_1.plus(20, MINUTES), OFF, STOP_3, COMPANY_1, BUS_47, PAN_1),
                                new Tap(5, DATE_1.plus(30, MINUTES), ON, STOP_3, COMPANY_1, BUS_47, PAN_1),
                                new Tap(6, DATE_1.plus(31, MINUTES), OFF, STOP_3, COMPANY_1, BUS_47, PAN_1),
                                new Tap(7, DATE_1.plus(1, HOURS), ON, STOP_3, COMPANY_1, BUS_19, PAN_1)),
                        List.of(
                                new Trip(DATE_1, DATE_1.plus(5, MINUTES), 300, STOP_1, STOP_2, 3.25, COMPANY_1, BUS_37, PAN_1, COMPLETED),
                                new Trip(DATE_1.plus(10, MINUTES), DATE_1.plus(20, MINUTES), 600, STOP_2, STOP_3, 5.5, COMPANY_1, BUS_47, PAN_1, COMPLETED),
                                new Trip(DATE_1.plus(30, MINUTES), DATE_1.plus(31, MINUTES), 60, STOP_3, STOP_3, 0, COMPANY_1, BUS_47, PAN_1, CANCELLED),
                                new Trip(DATE_1.plus(1, HOURS), null, 0, STOP_3, null, 7.3, COMPANY_1, BUS_19, PAN_1, INCOMPLETE)
                        )),
                arguments("Multiple trips, different pans",
                        List.of(
                                new Tap(1, DATE_1, ON, STOP_1, COMPANY_1, BUS_37, PAN_1),
                                new Tap(2, DATE_1.plus(10, MINUTES), ON, STOP_2, COMPANY_2, BUS_47, PAN_2),
                                new Tap(3, DATE_1.plus(5, MINUTES), OFF, STOP_2, COMPANY_1, BUS_37, PAN_1),
                                new Tap(4, DATE_1.plus(20, MINUTES), OFF, STOP_3, COMPANY_2, BUS_47, PAN_2),
                                new Tap(5, DATE_1.plus(30, MINUTES), ON, STOP_3, COMPANY_1, BUS_47, PAN_1),
                                new Tap(6, DATE_1.plus(1, HOURS), ON, STOP_3, COMPANY_2, BUS_19, PAN_2),
                                new Tap(7, DATE_1.plus(31, MINUTES), OFF, STOP_3, COMPANY_1, BUS_47, PAN_1)),
                        List.of(
                                new Trip(DATE_1, DATE_1.plus(5, MINUTES), 300, STOP_1, STOP_2, 3.25, COMPANY_1, BUS_37, PAN_1, COMPLETED),
                                new Trip(DATE_1.plus(10, MINUTES), DATE_1.plus(20, MINUTES), 600, STOP_2, STOP_3, 5.5, COMPANY_2, BUS_47, PAN_2, COMPLETED),
                                new Trip(DATE_1.plus(30, MINUTES), DATE_1.plus(31, MINUTES), 60, STOP_3, STOP_3, 0, COMPANY_1, BUS_47, PAN_1, CANCELLED),
                                new Trip(DATE_1.plus(1, HOURS), null, 0, STOP_3, null, 7.3, COMPANY_2, BUS_19, PAN_2, INCOMPLETE)
                        ))
        );
    }

    @MethodSource
    @ParameterizedTest(name = "{0}")
    void calculateTrips(String description, List<Tap> taps, List<Trip> expectedTrips) {
        assertThat(TripUsecase.calculateTrips(taps))
                .as(description)
                .containsExactlyElementsOf(expectedTrips);
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
        assertThat(TripUsecase.calculateAmount(from, to)).isEqualTo(expected);
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
