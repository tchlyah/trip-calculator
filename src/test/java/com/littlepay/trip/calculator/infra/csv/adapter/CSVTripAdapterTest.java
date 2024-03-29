package com.littlepay.trip.calculator.infra.csv.adapter;

import com.littlepay.trip.calculator.domain.model.Trip;
import com.littlepay.trip.calculator.domain.model.TripStatus;
import com.littlepay.trip.calculator.domain.port.TripPort;
import com.littlepay.trip.calculator.infra.csv.utils.DateUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.*;
import java.util.List;

import static com.littlepay.trip.calculator.domain.model.Stop.STOP_1;
import static com.littlepay.trip.calculator.domain.model.Stop.STOP_2;

public class CSVTripAdapterTest {

    TripPort tripPort = new CSVTripAdapter();

    @Test
    void should_write_csv_file() throws Exception {
        List<Trip> trips = List.of(
                new Trip(DateUtils.parse("22-01-2018 13:00:00"), DateUtils.parse("22-01-2018 13:05:00"), 300, STOP_1, STOP_2, 3.25d, "Company1", "Bus37", "5500005555555559", TripStatus.COMPLETED));
        Path path = Files.createTempFile("trips", ".csv");
        Assertions.assertThat(tripPort.writeFile(trips, path))
                .isEqualTo(Files.readString(Paths.get(ClassLoader.getSystemResource("csv/trips1.csv").toURI())));
    }
}
