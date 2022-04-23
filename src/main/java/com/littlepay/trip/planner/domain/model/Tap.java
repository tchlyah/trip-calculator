package com.littlepay.trip.planner.domain.model;

import java.time.LocalDateTime;

public record Tap(long id, LocalDateTime dateTimeUTC, TapType tapType, Stop stopId, String companyId, String busId,
                  String pan) {
}
