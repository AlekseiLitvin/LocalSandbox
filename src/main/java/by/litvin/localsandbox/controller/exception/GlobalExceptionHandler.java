package by.litvin.localsandbox.controller.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

//@ControllerAdvice
public class GlobalExceptionHandler {

    // TODO implement some handlers
    //    @ExceptionHandler()
    public ResponseEntity<Object> handle(Exception ex, WebRequest request) {
        return null;
    }

}
