package SimplifyPay.infrastructure.controllers;

import SimplifyPay.infrastructure.scenarios.AcidTestScenario;
import SimplifyPay.infrastructure.config.TestConfig;
import SimplifyPay.infrastructure.scenarios.UserTestScenario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

import java.math.BigDecimal;

import static SimplifyPay.infrastructure.scenarios.mock.MockingAuth.stubAuthorizationSuccess;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@EnableWireMock({@ConfigureWireMock(name = "auth_mock", port = 9000)})
@EnableAsync
@Import(TestConfig.class)
public class ControllerAcidTest {
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(ControllerAcidTest.class);

    final BigDecimal AMOUNT_0 = new BigDecimal("0.00");
    final BigDecimal AMOUNT_50 = new BigDecimal("50.00");
    final BigDecimal AMOUNT_100 = new BigDecimal("100.00");
    final BigDecimal AMOUNT_150 = new BigDecimal("150.00");
    final BigDecimal AMOUNT_200 = new BigDecimal("200.00");

    @Autowired
    private UserTestScenario userTestScenario;

    @Autowired
    private AcidTestScenario acidTestScenario;

    //Acid
    MockHttpServletResponse johnUserResponse;
    MockHttpServletResponse carlaUserResponse;
    MockHttpServletResponse mariaUserResponse;

    @BeforeEach
    void setup() throws Exception {
        johnUserResponse = acidTestScenario.creatingJohn();
        mariaUserResponse = acidTestScenario.creatingMaria();
        carlaUserResponse = acidTestScenario.creatingCarla();
    }

    @Test
    @DisplayName("Acid test.")
    @Async
    void acid() throws Exception {
        // Given: IDs dos usuários
        var johnId = userTestScenario.getIdFromResponse(johnUserResponse);
        var carlaId = userTestScenario.getIdFromResponse(carlaUserResponse);
        var mariaId = userTestScenario.getIdFromResponse(mariaUserResponse);

        // Atualiza os saldos
        var johnWalletToUpdate = userTestScenario.getWallet(johnId);
        johnWalletToUpdate.get().setBalance(AMOUNT_50); // vai mandar 50, fica com 0

        var carlaIdWalletToUpdate = userTestScenario.getWallet(carlaId);
        carlaIdWalletToUpdate.get().setBalance(AMOUNT_100);// vai receber 50, fica com 150 / depois vai mandar 50 fica com 100

        var mariaIdWalletToUpdate = userTestScenario.getWallet(mariaId);
        mariaIdWalletToUpdate.get().setBalance(AMOUNT_150);// vai receber 50, fica com 200

        userTestScenario.updateBalance(johnWalletToUpdate.get());
        userTestScenario.updateBalance(carlaIdWalletToUpdate.get());
        userTestScenario.updateBalance(mariaIdWalletToUpdate.get());

        var inicialJohnWallet = userTestScenario.getWallet(johnId);
        var inicialCarlaWallet = userTestScenario.getWallet(carlaId);
        var inicialMariaWallet = userTestScenario.getWallet(mariaId);

        logger.info("inicialJohnWallet: "+ inicialJohnWallet.get().getBalance());
        logger.info("inicialCarlaWallet: "+ inicialCarlaWallet.get().getBalance());
        logger.info("inicialMariaWallet: "+ inicialMariaWallet.get().getBalance());

        stubAuthorizationSuccess();

        // When
        acidTestScenario.executeTransferMoneyRequestWithThreadSleep(
                AMOUNT_50, johnId, carlaId, 10000
        );

        var johnPreviusWallet = userTestScenario.getWallet(johnId);
        var carlaPreviusWallet = userTestScenario.getWallet(carlaId);
        var mariaPreviusWallet = userTestScenario.getWallet(mariaId);

        logger.info("johnPreviusWallet: "+ johnPreviusWallet.get().getBalance());
        logger.info("carlaPreviusWallet: "+ carlaPreviusWallet.get().getBalance());
        logger.info("mariaPreviusWallet: "+ mariaPreviusWallet.get().getBalance());

        assertThat(johnPreviusWallet.get().getBalance()).isEqualByComparingTo(AMOUNT_0);
        assertThat(carlaPreviusWallet.get().getBalance()).isEqualByComparingTo(AMOUNT_150);
        assertThat(mariaPreviusWallet.get().getBalance()).isEqualByComparingTo(AMOUNT_150);

        // Aqui, John tem 0, Carla 150, Maria 150
        acidTestScenario.executeTransferMoneyRequestWithThreadSleep(
                AMOUNT_50, carlaId, mariaId, 5000
        );

        // Then
        var johnFinalWallet = userTestScenario.getWallet(johnId);
        var carlaFinalWallet = userTestScenario.getWallet(carlaId);
        var mariaFinalWallet = userTestScenario.getWallet(mariaId);

        logger.info("johnFinalWallet: "+ johnFinalWallet.get().getBalance());
        logger.info("carlaFinalWallet: "+ carlaFinalWallet.get().getBalance());
        logger.info("mariaFinalWallet: "+ mariaFinalWallet.get().getBalance());

        assertThat(johnFinalWallet.get().getBalance()).isEqualByComparingTo(AMOUNT_0);
        assertThat(carlaFinalWallet.get().getBalance()).isEqualByComparingTo(AMOUNT_100);
        assertThat(mariaFinalWallet.get().getBalance()).isEqualByComparingTo(AMOUNT_200);
    }

}
