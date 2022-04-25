package com.littlepay.trip.calculator.infra.csv.adapter;

import com.littlepay.trip.calculator.domain.model.*;
import com.littlepay.trip.calculator.domain.port.TapPort;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.littlepay.trip.calculator.infra.csv.utils.DateUtils.parse;
import static java.lang.String.format;

@Slf4j
@ApplicationScoped
public class CSVTapAdapter implements TapPort {
    
    @Override
    public List<Tap> readFile(Path path) {
        log.info("Read CSV Tap file from path '{}'", path);
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            try (var csvReader = new CSVReaderBuilder(reader)
                    .withSkipLines(1)
                    .build()) {

                return csvReader.readAll().stream()
                        .map(record -> new Tap(
                                Long.parseLong(record[0]),
                                parse(record[1].trim()),
                                TapType.valueOf(record[2].trim()),
                                Stop.from(record[3].trim()),
                                record[4].trim(),
                                record[5].trim(),
                                record[6].trim()))
                        .toList();
            }
        } catch (IOException | CsvException e) {
            log.error(format("Unable to read CSV file on path '%s'", path), e);
            throw new RuntimeException(e);
        }
    }

}
