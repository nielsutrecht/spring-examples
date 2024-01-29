package com.nibado.examples.spring.age.repository;

import com.nibado.examples.spring.age.BaseIntegrationTest;
import org.junit.jupiter.api.Test;

class SystemIntegrationTest extends BaseIntegrationTest {
    @Test
    public void test() {
        insertTestData();

        systemRepository.findAll().forEach(System.out::println);
        waypointRepository.findAll().forEach(System.out::println);
    }
}
