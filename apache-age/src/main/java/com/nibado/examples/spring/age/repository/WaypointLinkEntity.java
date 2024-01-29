package com.nibado.examples.spring.age.repository;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("waypoint_link")
public record WaypointLinkEntity(@Column("waypoint_from") String from, @Column("waypoint_to") String to, String type) {
}
