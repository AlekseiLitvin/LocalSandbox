package by.litvin.localsandbox.controller;

import by.litvin.localsandbox.LocalSandboxApplication;
import by.litvin.localsandbox.data.SomeData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = LocalSandboxApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SomeDataControllerTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate testClient = new TestRestTemplate();

    @Test
    void testHello() throws Exception {
        String url = "http://localhost:" + port + "/somedata/1";
        ResponseEntity<SomeData> response = testClient.getForEntity(url, SomeData.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1);
    }
}