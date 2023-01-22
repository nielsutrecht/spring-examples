package com.nibado.example.spring.secsingleuser;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.Stream;

import static com.nibado.example.spring.shared.Pair.pair;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ExampleApplicationIntegrationTest {
    @Autowired
    protected TestRestTemplate template;

    @LocalServerPort
    protected Integer port;

    //TestRestTemplate by default follows redirects
    private final HttpClient client = HttpClient.newHttpClient();

    @TestFactory
    public Stream<DynamicTest> should_present_a_login_screen() throws Exception {
        return Stream.of(
            "/",
            "/other"
        ).map(path -> DynamicTest.dynamicTest(path, () -> {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:" + port + path))
                .GET()
                .build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertThat(response.statusCode()).isEqualTo(UNAUTHORIZED.value());
        }));
    }

    @TestFactory
    public Stream<DynamicTest> should_present_page_when_authorized() throws Exception {
        return Stream.of(
            pair("/", "Spring Security Example :: Home"),
            pair("/other", "Spring Security Example :: Other")
        ).map(tc -> DynamicTest.dynamicTest(tc.first(), () -> {
            var request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:" + port + tc.first()))
                .GET()
                .build();

            var response = template.withBasicAuth("user", "pass")
                .getForEntity(tc.first(), String.class);

            assertThat(response.getStatusCode()).isEqualTo(OK);
            assertThat(response.getBody()).contains(tc.second());
        }));
    }

    @TestFactory
    public Stream<DynamicTest> api_endpoints_should_be_unprotected() throws Exception {
        return Stream.of(
            pair("/api", "{\"message\":\"Hello, World!\"}"),
            pair("/actuator/health", "{\"status\":\"UP\"}")
        ).map(tc -> DynamicTest.dynamicTest(tc.first(), () -> {
            var request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:" + port + tc.first()))
                .GET()
                .build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertThat(response.statusCode()).isEqualTo(OK.value());
            assertThat(response.body()).isEqualTo(tc.second());
        }));
    }

}
