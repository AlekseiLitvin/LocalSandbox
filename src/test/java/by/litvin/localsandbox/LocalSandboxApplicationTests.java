package by.litvin.localsandbox;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LocalSandboxApplicationTests extends IntegrationTestBase {

    @Test
    void contextLoads() {
        assertThat(2 + 2).isEqualTo(4);
    }

}
