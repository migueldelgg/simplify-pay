package SimplifyPay.infrastructure.config;

import SimplifyPay.infrastructure.scenarios.AcidTestScenario;
import SimplifyPay.infrastructure.scenarios.TransferMoneyTestScenario;
import SimplifyPay.infrastructure.scenarios.UserTestScenario;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    @Bean
    public TransferMoneyTestScenario transferMoneyTestScenario() {
        return new TransferMoneyTestScenario();
    }

    @Bean
    public UserTestScenario userTestScenario() {
        return new UserTestScenario();
    }

    @Bean
    public AcidTestScenario acidTestScenario() {
        return new AcidTestScenario();
    }
}
