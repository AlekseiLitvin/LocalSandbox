package by.litvin.localsandbox.controller.advice;

import io.minio.errors.MinioException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    @Test
    void testHandleBlobStorageException() {
        ResponseEntity<Object> response = new GlobalExceptionHandler().handle(new MinioException());

        assertThat(response.getStatusCodeValue()).isEqualTo(500);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}