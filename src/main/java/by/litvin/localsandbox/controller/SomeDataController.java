package by.litvin.localsandbox.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class SomeDataController {

    @GetMapping
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("It works");
    }

}
