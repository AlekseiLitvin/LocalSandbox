package by.litvin.localsandbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableCaching
public class LocalSandboxApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocalSandboxApplication.class, args);
    }
}
