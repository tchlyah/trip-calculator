package com.littlepay.trip.planner.infra.csv.adapter;

import com.littlepay.trip.planner.domain.model.Trip;
import com.littlepay.trip.planner.domain.model.TripStatus;
import com.littlepay.trip.planner.domain.port.TripPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.*;
import java.util.List;

public class CSVTripAdapterTest {

    TripPort tripPort = new CSVTripAdapter();

    @Test
    void should_write_csv_file() throws Exception {
        List<Trip> trips = List.of(
                new Trip("22-01-2018 13:00:00", "22-01-2018 13:05:00", 900, "Stop1", "Stop2", 3.25d, "Campany1", "B37", "5500005555555559", TripStatus.COMPLETED));
        Path path = Files.createTempFile("trips", ".csv");
        Assertions.assertThat(tripPort.writeFile(trips, path))
                .isEqualTo(Files.readString(Paths.get(ClassLoader.getSystemResource("csv/trips.csv").toURI())));
    }
}
