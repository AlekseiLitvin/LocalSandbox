package by.litvin.localsandbox.service.impl;

import by.litvin.localsandbox.repository.SomeDataRepository;
import by.litvin.localsandbox.service.SomeDataService;
import org.springframework.stereotype.Service;

@Service
public class SomeDataServiceImpl implements SomeDataService {

    private final SomeDataRepository someDataRepository;

    public SomeDataServiceImpl(SomeDataRepository someDataRepository) {
        this.someDataRepository = someDataRepository;
    }

}