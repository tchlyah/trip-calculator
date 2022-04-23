package com.littlepay.trip.planner.domain.usecase;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.littlepay.trip.planner.domain.model.*;
import com.littlepay.trip.planner.domain.port.TapPort;
import com.littlepay.trip.planner.domain.port.TripPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.littlepay.trip.planner.domain.model.Stop.*;
import static com.littlepay.trip.planner.domain.model.TripStatus.*;
import static java.time.temporal.ChronoUnit.SECONDS;

@Slf4j
@RequiredArgsConstructor
public class TripUsecase {

    private final TapPort tapPort;

    private final TripPort tripPort;

    private static final Map<Tuple<Stop, Stop>, Double> payGrid = Map.of(
            new Tuple<>(STOP_1, STOP_2), 3.25,
            new Tuple<>(STOP_2, STOP_3), 5.5,
            new Tuple<>(STOP_1, STOP_3), 7.3);

    private static final Map<Stop, Double> higherPayGrid = initializeHigherPayGrid();

    public void calculateTrips(Path tapsFile, Path tripsFile) {
        List<Tap> taps = tapPort.readFile(tapsFile);
        List<Trip> trips = calculateTrips(taps);
        tripPort.writeFile(trips, tripsFile);
    }

    public static List<Trip> calculateTrips(List<Tap> taps) {
        log.info("Calculate trips from {} taps", taps.size());
        Multimap<String, Trip> trips = HashMultimap.create();
        taps.stream()
                .sorted(Comparator.comparing(Tap::dateTimeUTC))
                .forEach(tap -> {
                    String key = String.join(":", tap.companyId(), tap.busId(), tap.pan());
                    Trip trip = trips.get(key).stream()
                            .filter(t -> t.status() == INCOMPLETE)
                            .findFirst()
                            .orElse(null);
                    if (trip == null) {
                        trip = new Trip(tap.dateTimeUTC(), null, 0,
                                tap.stopId(), null,
                                calculateHigherAmount(tap.stopId()),
                                tap.companyId(), tap.busId(), tap.pan(), INCOMPLETE);
                        trips.put(key, trip);
                    } else {
                        trips.remove(key, trip);
                        trip = trip
                                .withFinished(tap.dateTimeUTC())
                                .withToStopId(tap.stopId())
                                .withDurationSecs(SECONDS.between(trip.started(), tap.dateTimeUTC()));
                        if (trip.fromStopId() == tap.stopId()) {
                            trip = trip
                                    .withStatus(CANCELLED)
                                    .withChargeAmount(0);
                        } else {
                            trip = trip
                                    .withStatus(COMPLETED)
                                    .withChargeAmount(calculateAmount(trip.fromStopId(), tap.stopId()));
                        }
                        trips.put(key, trip);
                    }
                });
        List<Trip> result = trips.values().stream()
                .sorted(Comparator.comparing(Trip::started))
                .toList();
        log.info("Calculated {} trips", result.size());
        return result;
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
