package by.litvin.localsandbox;

import by.litvin.localsandbox.data.SomeData;
import by.litvin.localsandbox.repository.SomeDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class LocalSandboxApplication {

    private static final Logger log = LoggerFactory.getLogger(LocalSandboxApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LocalSandboxApplication.class, args);
    }

    @Bean
    public CommandLineRunner clr(SomeDataRepository repo) {
        return args -> {
            repo.save(new SomeData(UUID.randomUUID().toString()));
            repo.save(new SomeData(UUID.randomUUID().toString()));
            repo.save(new SomeData(UUID.randomUUID().toString()));
            repo.save(new SomeData(UUID.randomUUID().toString()));
        };

    }

}
