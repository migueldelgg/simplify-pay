package SimplifyPay.infrastructure.controllers;

import SimplifyPay.infrastructure.TestConfig;
import SimplifyPay.infrastructure.UserTestScenario;
import SimplifyPay.infrastructure.TransferMoneyTestScenario;
import org.junit.jupiter.api.AfterEach;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

import java.math.BigDecimal;

import static SimplifyPay.infrastructure.controllers.MockingAuth.stubAuthorizationForbidden;
import static SimplifyPay.infrastructure.controllers.MockingAuth.stubAuthorizationSuccess;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@EnableWireMock({@ConfigureWireMock(name = "auth_mock", port = 9000)})
@Import(TestConfig.class)
class ControllerTest {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(ControllerTest.class);

    // Constantes para teste
    final BigDecimal AMOUNT_0 = new BigDecimal("0.00");
    final BigDecimal AMOUNT_100 = new BigDecimal("100.00");
    final BigDecimal AMOUNT_1000 = new BigDecimal("1000.00");

    @Autowired
    private TransferMoneyTestScenario testScenario;

    @Autowired
    private UserTestScenario userTestScenario;

    MockHttpServletResponse commonUserResponse;
    MockHttpServletResponse merchantUserResponse;

    @BeforeEach
    void setup() throws Exception {
        commonUserResponse = userTestScenario.createCommonUser();
        merchantUserResponse = userTestScenario.createMerchantUser();
    }

    @Test
    @DisplayName("Merchant cant do it transfer money.")
    void should_not_be_possible_merchant_user_transfer_money_to_common_user() throws Exception {
        // Given: IDs dos usuários
        var commonUserId = userTestScenario.getIdFromResponse(commonUserResponse);
        var merchantUserId = userTestScenario.getIdFromResponse(merchantUserResponse);

        // Atualiza os saldos
        var commonWalletToUpdate = userTestScenario.getWallet(commonUserId);
        commonWalletToUpdate.get().setBalance(AMOUNT_1000);

        var merchantWalletToUpdate = userTestScenario.getWallet(merchantUserId);
        merchantWalletToUpdate.get().setBalance(AMOUNT_1000);

        userTestScenario.updateBalance(commonWalletToUpdate.get());
        userTestScenario.updateBalance(merchantWalletToUpdate.get());

        stubAuthorizationSuccess();

        // When
        var response = testScenario.executeTransferMoneyRequest(
                AMOUNT_100, merchantUserId, commonUserId
        );

        // Then
        var commonWallet = userTestScenario.getWallet(commonUserId);
        var merchantWallet = userTestScenario.getWallet(merchantUserId);
        assertThat(commonWallet.get().getBalance()).isEqualByComparingTo(AMOUNT_1000);
        assertThat(merchantWallet.get().getBalance()).isEqualByComparingTo(AMOUNT_1000);

        var expectedResponse = testScenario
                .expectedErrorResponse(
                "Bad Request",
                "Comerciantes não estão autorizados fazer transferência."
                );
        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.getContentAsString()).isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Should not allow a transfer if the balance is insufficient.")
    void it_should_not_be_possible_to_transfer_if_the_balance_is_insufficient() throws Exception {
        // Given: IDs dos usuários
        var commonUserId = userTestScenario.getIdFromResponse(commonUserResponse);
        var merchantUserId = userTestScenario.getIdFromResponse(merchantUserResponse);

        // Atualiza os saldos
        var commonWalletToUpdate = userTestScenario.getWallet(commonUserId);
        commonWalletToUpdate.get().setBalance(AMOUNT_0);

        var merchantWalletToUpdate = userTestScenario.getWallet(merchantUserId);
        merchantWalletToUpdate.get().setBalance(AMOUNT_100);

        userTestScenario.updateBalance(commonWalletToUpdate.get());
        userTestScenario.updateBalance(merchantWalletToUpdate.get());

        // Mock do autorizador
        stubAuthorizationSuccess();

        // When: Tenta realizar a transferência
        var response = testScenario.executeTransferMoneyRequest(
                AMOUNT_100, commonUserId, merchantUserId
        );

        // Then: Verifica saldos e resposta
        var commonWalletAfter = userTestScenario.getWallet(commonUserId);
        var merchantWalletAfter = userTestScenario.getWallet(merchantUserId);

        assertThat(commonWalletAfter.get().getBalance()).isEqualByComparingTo(AMOUNT_0);
        assertThat(merchantWalletAfter.get().getBalance()).isEqualByComparingTo(AMOUNT_100);

        // Valida resposta do erro
        var expectedResponse = testScenario
                .expectedErrorResponse(
                        "Bad Request",
                        "Saldo insuficiente para realizar a transação."
                );

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.getContentAsString()).isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("It should be possible to transfer if the balance is sufficient.")
    void it_should_be_possible_to_transfer_if_the_balance_is_sufficient() throws Exception {
        // Given: IDs dos usuários
        var commonUserId = userTestScenario.getIdFromResponse(commonUserResponse);
        var merchantUserId = userTestScenario.getIdFromResponse(merchantUserResponse);

        // Atualiza os saldos
        var commonWalletToUpdate = userTestScenario.getWallet(commonUserId);
        commonWalletToUpdate.get().setBalance(AMOUNT_1000);

        var merchantWalletToUpdate = userTestScenario.getWallet(merchantUserId);
        merchantWalletToUpdate.get().setBalance(AMOUNT_1000);

        userTestScenario.updateBalance(commonWalletToUpdate.get());
        userTestScenario.updateBalance(merchantWalletToUpdate.get());

        stubAuthorizationSuccess();

        // When: Tenta realizar a transferência
        var response = testScenario.executeTransferMoneyRequest(
                AMOUNT_100, commonUserId, merchantUserId
        );

        // Then
        var commonWalletAfter = userTestScenario.getWallet(commonUserId);
        var merchantWalletAfter = userTestScenario.getWallet(merchantUserId);

        assertThat(commonWalletAfter.get().getBalance()).isEqualByComparingTo(AMOUNT_1000.subtract(AMOUNT_100));
        assertThat(merchantWalletAfter.get().getBalance()).isEqualByComparingTo(AMOUNT_1000.add(AMOUNT_100));

        var expectedResponse = testScenario.expectedSuccessResponse(
                commonWalletAfter.get().getId(),
                merchantWalletAfter.get().getId(),
                AMOUNT_100
        );

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).isEqualTo(expectedResponse);
    }

    @Test
    void shouldThrowExceptionWhenAuthorizationFails() throws Exception {
        // Given: IDs dos usuários
        var commonUserId = userTestScenario.getIdFromResponse(commonUserResponse);
        var merchantUserId = userTestScenario.getIdFromResponse(merchantUserResponse);

        // Atualiza os saldos
        var commonWalletToUpdate = userTestScenario.getWallet(commonUserId);
        commonWalletToUpdate.get().setBalance(AMOUNT_1000);

        var merchantWalletToUpdate = userTestScenario.getWallet(merchantUserId);
        merchantWalletToUpdate.get().setBalance(AMOUNT_1000);

        userTestScenario.updateBalance(commonWalletToUpdate.get());
        userTestScenario.updateBalance(merchantWalletToUpdate.get());

        stubAuthorizationForbidden();

        // When: Tenta realizar a transferência
        var response = testScenario.executeTransferMoneyRequest(
                AMOUNT_100, commonUserId, merchantUserId
        );

        assertThat(response.getStatus()).isEqualTo(403);
    }

    @Test
    void shouldTransferMoneyWhenAuthorizationWorks() throws Exception {
        // Given: IDs dos usuários
        var commonUserId = userTestScenario.getIdFromResponse(commonUserResponse);
        var merchantUserId = userTestScenario.getIdFromResponse(merchantUserResponse);

        // Atualiza os saldos
        var commonWalletToUpdate = userTestScenario.getWallet(commonUserId);
        commonWalletToUpdate.get().setBalance(AMOUNT_1000);

        var merchantWalletToUpdate = userTestScenario.getWallet(merchantUserId);
        merchantWalletToUpdate.get().setBalance(AMOUNT_1000);

        userTestScenario.updateBalance(commonWalletToUpdate.get());
        userTestScenario.updateBalance(merchantWalletToUpdate.get());

        stubAuthorizationSuccess();

        // When: Tenta realizar a transferência
        var response = testScenario.executeTransferMoneyRequest(
                AMOUNT_100, commonUserId, merchantUserId
        );

        // Then
        var commonWalletAfter = userTestScenario.getWallet(commonUserId);
        var merchantWalletAfter = userTestScenario.getWallet(merchantUserId);

        assertThat(commonWalletAfter.get().getBalance()).isEqualByComparingTo(AMOUNT_1000.subtract(AMOUNT_100));
        assertThat(merchantWalletAfter.get().getBalance()).isEqualByComparingTo(AMOUNT_1000.add(AMOUNT_100));

        var expectedResponse = testScenario.expectedSuccessResponse(
                commonWalletAfter.get().getId(),
                merchantWalletAfter.get().getId(),
                AMOUNT_100
        );

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).isEqualTo(expectedResponse);
    }

    @AfterEach
    void cleanupDatabase() throws Exception {
        var commonId = userTestScenario.getIdFromResponse(commonUserResponse);
        var merchantId = userTestScenario.getIdFromResponse(merchantUserResponse);

        var payerWallet = userTestScenario.getWallet(commonId);

        userTestScenario.deleteTransactionByWalletPayerId(payerWallet.get().getId());
        userTestScenario.deleteUserAndWallet(commonId);
        userTestScenario.deleteUserAndWallet(merchantId);
    }
}