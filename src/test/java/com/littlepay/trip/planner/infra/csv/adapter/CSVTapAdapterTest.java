package com.littlepay.trip.planner.infra.csv.adapter;

import com.littlepay.trip.planner.domain.model.Tap;
import com.littlepay.trip.planner.domain.model.TapType;
import com.littlepay.trip.planner.domain.port.TapPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

public class CSVTapAdapterTest {

    TapPort csvTapAdapter = new CSVTapAdapter();

    @Test
    void should_read_csv_file() throws URISyntaxException {
        List<Tap> taps = csvTapAdapter.readFile(Paths.get(ClassLoader.getSystemResource("csv/taps.csv").toURI()));
        Assertions.assertThat(taps)
                .contains(
                        new Tap(1, "22-01-2018 13:00:00", TapType.ON, "Stop1", "Company1", "Bus37", "5500005555555559"),
                        new Tap(2, "22-01-2018 13:05:00", TapType.OFF, "Stop2", "Company1", "Bus37", "5500005555555559"));
    }
}
