package com.nibado.examples.spring.age.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("waypoint")
public record WaypointEntity(@Id String symbol, String systemSymbol, String type, int x, int y) {
}
