package com.littlepay.trip.planner.domain.usecase;

import com.littlepay.trip.planner.domain.model.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.littlepay.trip.planner.domain.model.Stop.*;
import static com.littlepay.trip.planner.domain.model.TapType.OFF;
import static com.littlepay.trip.planner.domain.model.TapType.ON;
import static com.littlepay.trip.planner.domain.model.TripStatus.*;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.function.Function.identity;

public class TripUsecase {

    private static final Map<Tuple<Stop, Stop>, Double> payGrid = Map.of(
            new Tuple<>(STOP_1, STOP_2), 3.25,
            new Tuple<>(STOP_2, STOP_3), 5.5,
            new Tuple<>(STOP_1, STOP_3), 7.3);

    private static final Map<Stop, Double> higherPayGrid = initializeHigherPayGrid();

    public static List<Trip> calculateTrips(List<Tap> taps) {
        return taps.stream()
                .sorted(Comparator.comparing(Tap::dateTimeUTC))
                .map(tap ->
                        new Trip(tap.tapType() == ON ? tap.dateTimeUTC() : null, tap.tapType() == OFF ? tap.dateTimeUTC() : null,
                                0,
                                tap.tapType() == ON ? tap.stopId() : null, tap.tapType() == OFF ? tap.stopId() : null,
                                calculateHigherAmount(tap.stopId()),
                                tap.companyId(), tap.busId(), tap.pan(), INCOMPLETE))
                .collect(Collectors.toMap(trip -> trip.companyId() + trip.busId() + trip.pan(), identity(), TripUsecase::mergeTrips))
                .values().stream()
                .sorted(Comparator.comparing(Trip::started))
                .toList();
    }

    private static Trip mergeTrips(Trip trip1, Trip trip2) {
        var started = findFirst(trip1, trip2, Trip::started);
        var finished = findFirst(trip1, trip2, Trip::finished);
        var durationSecs = started != null && finished != null ? SECONDS.between(started, finished) : 0L;
        var from = findFirst(trip1, trip2, Trip::fromStopId);
        var to = findFirst(trip1, trip2, Trip::toStopId);
        TripStatus status;
        double chargeAmount;

        if (from == null) {
            throw new IllegalArgumentException("Trip should start somewhere");
        }

        if (from == to) {
            status = CANCELLED;
            chargeAmount = 0d;
        } else {
            status = COMPLETED;
            chargeAmount = calculateAmount(from, to);
        }

        return new Trip(started, finished, durationSecs, from, to, chargeAmount, trip1.companyId(), trip1.busId(), trip1.pan(), status);
    }

    private static <T> T findFirst(Trip trip1, Trip trip2, Function<Trip, T> getter) {
        return Stream.of(trip1, trip2)
                .map(getter)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    static double calculateAmount(Stop from, Stop to) {
        return payGrid.get(new Tuple<>(from, to));
    }

    static double calculateHigherAmount(Stop stop) {
        return higherPayGrid.get(stop);
    }

    private static Map<Stop, Double> initializeHigherPayGrid() {
        return payGrid.entrySet().stream()
                .flatMap(e -> Stream.of(new Tuple<>(e.getKey().v1(), e.getValue()), new Tuple<>(e.getKey().v2(), e.getValue())))
                .collect(Collectors.toMap(Tuple::v1, Tuple::v2, (a1, a2) -> a1 >= a2 ? a1 : a2));
    }
}
