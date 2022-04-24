package com.littlepay.trip.calculator.domain.port;

import com.littlepay.trip.calculator.domain.model.Trip;

import java.nio.file.Path;
import java.util.List;

public interface TripPort {

    String writeFile(List<Trip> trips, Path path);
}
