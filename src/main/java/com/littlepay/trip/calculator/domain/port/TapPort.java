package com.littlepay.trip.calculator.domain.port;

import com.littlepay.trip.calculator.domain.model.Tap;

import java.nio.file.Path;
import java.util.List;

public interface TapPort {

    List<Tap> readFile(Path path);
}
