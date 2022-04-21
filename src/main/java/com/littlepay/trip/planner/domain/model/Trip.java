package com.littlepay.trip.planner.domain.model;

public record Trip(
        String started,
        String finished,
        long durationSecs,
        String fromStopId,
        String toStopId,
        double chargeAmount,
        String companyId,
        String busId,
        String pan,
        TripStatus status) {
}
