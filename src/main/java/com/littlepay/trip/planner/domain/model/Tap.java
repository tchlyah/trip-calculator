package com.littlepay.trip.planner.domain.model;

import java.time.Instant;

public record Tap(long id, String dateTimeUTC, TapType tapType, String stopId, String companyId, String busId,
                  String pan) {
}
