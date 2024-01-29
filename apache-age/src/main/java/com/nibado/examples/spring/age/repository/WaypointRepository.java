package com.nibado.examples.spring.age.repository;

import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Repository
public class WaypointRepository {
    private final NamedParameterJdbcTemplate template;
    private final JdbcAggregateTemplate aggregateTemplate;

    public WaypointRepository(NamedParameterJdbcTemplate template, JdbcAggregateTemplate aggregateTemplate) {
        this.template = template;
        this.aggregateTemplate = aggregateTemplate;
    }

    public Iterable<WaypointEntity> findAll() {
        return aggregateTemplate.findAll(WaypointEntity.class);
    }

    public void insert(WaypointEntity entity) {
        aggregateTemplate.insert(entity);
    }

    public void link(String waypointOne, String waypointTwo, String type) {
        template.update(Queries.INSERT_LINK, Map.of("from", waypointOne, "to", waypointTwo, "type", type));
        template.update(Queries.INSERT_LINK, Map.of("from", waypointTwo, "to", waypointOne, "type", type));
    }

    private static class Queries {
        private static final String FIELDS = "symbol, system_symbol, type, x, y";
        private static final String SELECT_ALL = String.format("SELECT %s FROM waypoint", FIELDS);
        private static final String SELECT_BY_ID = String.format("%s WHERE symbol = :symbol", SELECT_ALL);

        private static final String INSERT_LINK = "INSERT INTO waypoint_link (waypoint_from, waypoint_to, type) VALUES (:from, :to, :type)";
    }

    private static class Rowmapper implements RowMapper<SystemEntity> {
        @Override
        public SystemEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new SystemEntity(
                rs.getString("symbol"),
                rs.getInt("x"),
                rs.getInt("y"));
        }
    }
}
