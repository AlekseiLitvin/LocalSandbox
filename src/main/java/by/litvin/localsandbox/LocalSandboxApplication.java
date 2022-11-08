package by.litvin.localsandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan
public class LocalSandboxApplication {

    private static final Logger log = LoggerFactory.getLogger(LocalSandboxApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LocalSandboxApplication.class, args);
    }

    @Bean
    public CommandLineRunner clr() {
        return args -> {

        };

    }

}
