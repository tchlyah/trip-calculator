package com.littlepay.trip.planner.domain.usecase;

import com.littlepay.trip.planner.domain.model.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.littlepay.trip.planner.domain.model.Stop.*;

public class TripUsecase {

    private static final Map<Tuple<Stop, Stop>, Double> payGrid = Map.of(
            new Tuple<>(STOP_1, STOP_2), 3.25,
            new Tuple<>(STOP_2, STOP_3), 5.5,
            new Tuple<>(STOP_1, STOP_3), 7.3);

    private static final Map<Stop, Double> higherPayGrid = initializeHigherPayGrid();

    public List<Trip> calculateTrips(List<Tap> taps) {
        return null;
    }

    static double calculateHigherAmount(Stop from, Stop to) {
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
