package com.littlepay.trip.planner.domain.port;

import com.littlepay.trip.planner.domain.model.Tap;

import java.nio.file.Path;
import java.util.List;

public interface TapPort {

    List<Tap> readFile(Path path);
}
