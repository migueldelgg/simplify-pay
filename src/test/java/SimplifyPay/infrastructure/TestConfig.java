package SimplifyPay.infrastructure;

import com.github.tomakehurst.wiremock.WireMockServer;
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
    public WireMockServer wireMockServer() {
        return new WireMockServer(8080);
    }

}
