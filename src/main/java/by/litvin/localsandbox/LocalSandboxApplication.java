package by.litvin.localsandbox;

import by.litvin.localsandbox.data.SomeData;
import by.litvin.localsandbox.data.repository.SomeDataRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class LocalSandboxApplication {

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
            repo.save(new SomeData(UUID.randomUUID().toString()));
        };

    }

}
