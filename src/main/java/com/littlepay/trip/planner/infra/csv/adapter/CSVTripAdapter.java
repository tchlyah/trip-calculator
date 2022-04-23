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

import static com.littlepay.trip.planner.infra.csv.utils.DateUtils.format;
import static java.lang.String.format;

@Slf4j
public class CSVTripAdapter implements TripPort {

    @Override
    public String writeFile(List<Trip> trips, Path path) {
        try {
            try (var writer = new CSVWriter(new FileWriter(path.toString()))) {
                writer.writeNext(new String[]{"Started", "Finished", "DurationSecs", "FromStopId", "ToStopId", "ChargeAmount", "CompanyId", "BusID", "PAN", "Status"});
                trips.stream()
                        .map(trip ->
                                new String[]{format(trip.started()), format(trip.finished()), String.valueOf(trip.durationSecs()), trip.fromStopId().toString(), trip.toStopId().toString(), String.valueOf(trip.chargeAmount()), trip.companyId(), trip.busId(), trip.pan(), trip.status().toString()})
                        .forEach(writer::writeNext);
            }
            return Files.readString(path);
        } catch (IOException e) {
            log.error(format("Unable to write CSV file to path '%s'", path), e);
            throw new RuntimeException(e);
        }
    }
}
