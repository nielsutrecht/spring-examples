package com.nibado.examples.spring.age.age;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Graph {
    private final NamedParameterJdbcTemplate template;

    public Graph(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public void insertSystem(String name, String title) {
        template.update(Cypher.CREATE_SYSTEM, Map.of("title", title, "name", name));
    }

    private static class Cypher {
        private static final String CREATE_SYSTEM = """
            SELECT * FROM cypher('graph_name', $$
                CREATE (:System {name: :name, title: :title})
            $$) as (n agtype);
            """;
    }
}
