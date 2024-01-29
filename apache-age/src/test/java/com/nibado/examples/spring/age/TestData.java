package com.nibado.examples.spring.age;

import com.nibado.examples.spring.age.repository.SystemEntity;
import com.nibado.examples.spring.age.repository.WaypointEntity;

import java.util.List;

public class TestData {
    public static final List<SystemEntity> SYSTEMS = List.of(
        new SystemEntity("X1-ZA40", 34, 40),
        new SystemEntity("X1-TQ4", 24, 5)
    );

    public static final List<WaypointEntity> WAYPOINTS = List.of(
        new WaypointEntity("X1-ZA40-15970B", "X1-ZA40", "PLANET", 10, 0),
        new WaypointEntity("X1-ZA40-28549E", "X1-ZA40", "JUMP_GATE", 2, 75),

        new WaypointEntity("X1-TQ4-38810X", "X1-TQ4", "PLANET", 10, 0),
        new WaypointEntity("X1-TQ4-43213E", "X1-TQ4", "ORBITAL_STATION", 10, 0),
        new WaypointEntity("X1-TQ4-76314C", "X1-TQ4", "JUMP_GATE", 2, 75)
    );

    public static final List<String> JUMP_GATES =  WAYPOINTS.stream()
        .filter(wp -> wp.type().equals("JUMP_GATE"))
        .map(WaypointEntity::symbol)
        .toList();
}
