package com.nibado.examples.spring.age.age;

import com.nibado.examples.spring.age.BaseIntegrationTest;
import org.junit.jupiter.api.Test;

class GraphIntegrationTest extends BaseIntegrationTest {
    @Test
    public void test() {
        graph.insertSystem("The Name", "The Title");
    }
}
