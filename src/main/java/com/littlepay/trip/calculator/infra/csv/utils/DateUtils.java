package com.littlepay.trip.calculator.infra.csv.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public interface DateUtils {

    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    static LocalDateTime parse(String date) {
        return LocalDateTime.parse(date, DATE_FORMATTER);
    }

    static String format(LocalDateTime date) {
        return Optional.ofNullable(date)
                .map(d -> d.format(DATE_FORMATTER))
                .orElse(null);
    }
}
