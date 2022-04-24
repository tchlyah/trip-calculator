package com.littlepay.trip.calculator.infra.csv.adapter;

import com.littlepay.trip.calculator.domain.model.Tap;
import com.littlepay.trip.calculator.domain.model.TapType;
import com.littlepay.trip.calculator.domain.port.TapPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

import static com.littlepay.trip.calculator.domain.model.Stop.STOP_1;
import static com.littlepay.trip.calculator.domain.model.Stop.STOP_2;
import static com.littlepay.trip.calculator.infra.csv.utils.DateUtils.parse;

public class CSVTapAdapterTest {

    TapPort csvTapAdapter = new CSVTapAdapter();

    @Test
    void should_read_csv_file() throws URISyntaxException {
        List<Tap> taps = csvTapAdapter.readFile(Paths.get(ClassLoader.getSystemResource("csv/taps1.csv").toURI()));
        Assertions.assertThat(taps)
                .contains(
                        new Tap(1, parse("22-01-2018 13:00:00"), TapType.ON, STOP_1, "Company1", "Bus37", "5500005555555559"),
                        new Tap(2, parse("22-01-2018 13:05:00"), TapType.OFF, STOP_2, "Company1", "Bus37", "5500005555555559"));
    }
}
