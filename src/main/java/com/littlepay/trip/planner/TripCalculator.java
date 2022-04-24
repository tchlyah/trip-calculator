package com.littlepay.trip.planner;

import com.littlepay.trip.planner.domain.usecase.TripUsecase;
import com.littlepay.trip.planner.infra.csv.adapter.CSVTapAdapter;
import com.littlepay.trip.planner.infra.csv.adapter.CSVTripAdapter;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.nio.file.Path;

@Command(name = "tripCalculator", mixinStandardHelpOptions = true, version = "0.1", description = "Calculate trips from taps")
public class TripCalculator implements Runnable {

    @Parameters(index = "0", description = "CSV file containing taps.")
    private Path tapsFile;

    @Parameters(index = "1", description = "CSV destination file to save calculated trips.")
    private Path tripsFile;

    private static final TripUsecase tripUsecase = new TripUsecase(new CSVTapAdapter(), new CSVTripAdapter());

    @Override
    public void run() {
        tripUsecase.calculateTrips(tapsFile, tripsFile);
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new TripCalculator()).execute(args);
        System.exit(exitCode);
    }
}
