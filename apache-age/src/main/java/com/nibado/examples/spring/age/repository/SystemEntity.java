package com.nibado.examples.spring.age.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("solar_system")
public record SystemEntity(@Id String symbol, int x, int y) {
}
