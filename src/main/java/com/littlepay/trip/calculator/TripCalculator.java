package com.littlepay.trip.calculator;

import com.littlepay.trip.calculator.domain.usecase.TripUsecase;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.nio.file.Path;

@RequiredArgsConstructor
@Command(name = "tripCalculator", mixinStandardHelpOptions = true, version = "0.1", description = "Calculate trips from taps")
public class TripCalculator implements Runnable {

    @Parameters(index = "0", description = "CSV file containing taps.")
    private Path tapsFile;

    @Parameters(index = "1", description = "CSV destination file to save calculated trips.")
    private Path tripsFile;

    private final TripUsecase tripUsecase;

    @Override
    public void run() {
        tripUsecase.calculateTrips(tapsFile, tripsFile);
    }
}
