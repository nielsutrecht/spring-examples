package com.nibado.examples.spring.age;

import com.nibado.examples.spring.age.age.Graph;
import com.nibado.examples.spring.age.repository.SystemRepository;
import com.nibado.examples.spring.age.repository.WaypointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
public abstract class BaseIntegrationTest {
    @Autowired
    protected SystemRepository systemRepository;

    @Autowired
    protected WaypointRepository waypointRepository;

    @Autowired
    protected Graph graph;

    private static DockerImageName APACHE_AGE_IMAGE = DockerImageName.parse("apache/age:latest")
        .asCompatibleSubstituteFor("postgres");

    protected void insertTestData() {
        TestData.SYSTEMS.forEach(systemRepository::insert);
        TestData.WAYPOINTS.forEach(waypointRepository::insert);

        waypointRepository.link(TestData.JUMP_GATES.get(0), TestData.JUMP_GATES.get(1), "JUMP_GATE");
    }

    @Container
    static PostgreSQLContainer postgresqlContainer = (PostgreSQLContainer) new PostgreSQLContainer(APACHE_AGE_IMAGE)
        .withDatabaseName("test").withUsername("postgres").withPassword("postgres");

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
    }
}
