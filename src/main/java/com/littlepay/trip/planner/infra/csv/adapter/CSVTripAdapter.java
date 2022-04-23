package com.littlepay.trip.planner.infra.csv.adapter;

import com.littlepay.trip.planner.domain.model.Trip;
import com.littlepay.trip.planner.domain.port.TripPort;
import com.opencsv.CSVWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static com.littlepay.trip.planner.infra.csv.utils.DateUtils.format;
import static com.opencsv.ICSVWriter.*;
import static java.lang.String.format;

@Slf4j
public class CSVTripAdapter implements TripPort {

    @Override
    public String writeFile(List<Trip> trips, Path path) {
        log.info("Write CSV Trip file to path '{}'", path);
        try {
            try (var writer = new CSVWriter(new FileWriter(path.toString()), DEFAULT_SEPARATOR, NO_QUOTE_CHARACTER, DEFAULT_ESCAPE_CHARACTER, DEFAULT_LINE_END)) {
                writer.writeNext(new String[]{"Started", "Finished", "DurationSecs", "FromStopId", "ToStopId", "ChargeAmount", "CompanyId", "BusID", "PAN", "Status"});
                trips.stream()
                        .map(trip ->
                                new String[]{format(trip.started()), format(trip.finished()), String.valueOf(trip.durationSecs()), toStringOrNull(trip.fromStopId()), toStringOrNull(trip.toStopId()), String.valueOf(trip.chargeAmount()), trip.companyId(), trip.busId(), trip.pan(), trip.status().toString()})
                        .forEach(writer::writeNext);
            }
            return Files.readString(path);
        } catch (IOException e) {
            log.error(format("Unable to write CSV file to path '%s'", path), e);
            throw new RuntimeException(e);
        }
    }

    private <T> String toStringOrNull(T t) {
        return Optional.ofNullable(t)
                .map(Object::toString)
                .orElse(null);
    }
}
