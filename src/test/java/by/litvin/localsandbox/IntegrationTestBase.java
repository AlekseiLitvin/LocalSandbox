package by.litvin.localsandbox;

import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@Tag("integration_test")
//@Testcontainers
@SpringBootTest
public class IntegrationTestBase {
    //    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres")
            .withDatabaseName("test_db")
            .withUsername("pg")
            .withPassword("root");

    static {
        postgres.start();
    }

    @DynamicPropertySource
    public static void initDb(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
}
