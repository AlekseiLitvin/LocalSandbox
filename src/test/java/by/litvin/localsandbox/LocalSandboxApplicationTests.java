package by.litvin.localsandbox;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LocalSandboxApplicationTests {

    @Test
    void contextLoads() {
        assertThat(2 + 2).isEqualTo(4);
    }

    @Test
    void fail() {
        assertThat(2 + 2).isEqualTo(3);
    }

}
