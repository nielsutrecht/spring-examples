package com.nibado.examples.spring.age.repository;

import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class SystemRepository {
    private final NamedParameterJdbcTemplate template;
    private final JdbcAggregateTemplate aggregateTemplate;

    public SystemRepository(NamedParameterJdbcTemplate template, JdbcAggregateTemplate aggregateTemplate) {
        this.template = template;
        this.aggregateTemplate = aggregateTemplate;
    }

    public List<SystemEntity> findAll() {
        return template.query(Queries.SELECT_ALL, Map.of(), DataClassRowMapper.newInstance(SystemEntity.class));
    }

    public void insert(SystemEntity entity) {
        aggregateTemplate.insert(entity);
    }

    private static class Queries {
        private static final String FIELDS = "symbol, x, y";
        private static final String SELECT_ALL = String.format("SELECT %s FROM solar_system", FIELDS);
        private static final String SELECT_BY_ID = String.format("%s WHERE symbol = :symbol", SELECT_ALL);
    }
}
