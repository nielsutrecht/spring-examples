package com.nibado.example.spring.formfile;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ExampleApplicationIntegrationTest {
    @Autowired
    protected TestRestTemplate template;

    @Test
    void form_with_file_should_contain_correct_information() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        var multipart = new LinkedMultiValueMap<String, Object>();
        multipart.add("number", "1984");
        multipart.add("date", "2023-12-31");
        multipart.add("file", new ClassPathResource("/data/testfile.txt"));

        var request = new HttpEntity<>(multipart, headers);

        var response = template.postForEntity("/submit", request, String.class);

        assertThat(response.getBody()).contains("<span>2023-12-31</span>");
        assertThat(response.getBody()).contains("<span>1984</span>");
        assertThat(response.getBody()).contains("<span>testfile.txt</span>");
        assertThat(response.getBody()).contains("<span>26</span>");
    }

    @Test
    void form_without_file_should_contain_correct_information() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        var multipart = new LinkedMultiValueMap<String, Object>();
        multipart.add("number", "2023");
        multipart.add("date", "2023-11-30");

        var request = new HttpEntity<>(multipart, headers);

        var response = template.postForEntity("/submit", request, String.class);

        assertThat(response.getBody()).contains("<span>2023-11-30</span>");
        assertThat(response.getBody()).contains("<span>2023</span>");
        assertThat(response.getBody()).contains("No file uploaded");
    }
}
