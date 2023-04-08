package by.litvin.localsandbox.controller.exception;

import io.minio.errors.MinioException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MinioException.class)
    public ResponseEntity<Object> handle(Exception ex, WebRequest request) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }

}
