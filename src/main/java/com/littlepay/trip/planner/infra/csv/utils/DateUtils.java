package com.littlepay.trip.planner.infra.csv.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface DateUtils {

    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    static LocalDateTime parse(String date) {
        return LocalDateTime.parse(date, DATE_FORMATTER);
    }

    static String format(LocalDateTime date) {
        return date.format(DATE_FORMATTER);
    }
}
