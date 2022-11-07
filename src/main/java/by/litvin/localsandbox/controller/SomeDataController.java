package by.litvin.localsandbox.controller;

import by.litvin.localsandbox.data.SomeData;
import by.litvin.localsandbox.repository.SomeDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/somedata")
public class SomeDataController {

    private static Logger log = LoggerFactory.getLogger(SomeDataController.class);

    private final SomeDataRepository someDataRepository;

    @Autowired
    public SomeDataController(SomeDataRepository someDataRepository) {
        this.someDataRepository = someDataRepository;
    }

    @GetMapping
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("It works");
    }

    @GetMapping("/{id}")
    public ResponseEntity<SomeData> findById(@PathVariable Long id) {
        SomeData created = someDataRepository.findById(id).orElseThrow();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(created);
    }

    //    @PostMapping
    //    public SomeData create(@RequestBody SomeDataDto dto) {
    //        SomeData someData = new SomeData(dto.getText());
    //        return someDataRepository.save(someData);
    //    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        someDataRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
