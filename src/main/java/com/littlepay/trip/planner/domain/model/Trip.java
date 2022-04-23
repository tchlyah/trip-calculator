package com.littlepay.trip.planner.domain.model;

import lombok.With;

import java.time.LocalDateTime;

@With
public record Trip(
        LocalDateTime started,
        LocalDateTime finished,
        long durationSecs,
        Stop fromStopId,
        Stop toStopId,
        double chargeAmount,
        String companyId,
        String busId,
        String pan,
        TripStatus status) {
}
