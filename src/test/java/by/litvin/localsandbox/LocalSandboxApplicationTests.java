package by.litvin.localsandbox;

import by.litvin.localsandbox.service.IntegrationTestBase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LocalSandboxApplicationTests extends IntegrationTestBase {

    @Test
    void contextLoads() {
        assertThat(2 + 2).isEqualTo(4);
    }

    @Test
    @Disabled
    void fail() {
        assertThat(2 + 2).isEqualTo(5);
    }

}
