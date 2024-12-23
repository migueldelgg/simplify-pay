package SimplifyPay.adapters.controllers;

import SimplifyPay.adapters.UserTestScenario;
import SimplifyPay.adapters.TransferMoneyTestScenario;
import SimplifyPay.application.dtos.TransferMoneyResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
class ControllerTest {
    // Constantes para teste
    final BigDecimal AMOUNT_100 = new BigDecimal("100.00");
    final BigDecimal AMOUNT_0 = new BigDecimal("0.00");
    final BigDecimal INITIAL_BALANCE = new BigDecimal("1000.00");

    @Autowired
    private TransferMoneyTestScenario testScenario;

    @Autowired
    private UserTestScenario userTestScenario;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Merchant cant do it transfer money.")
    void should_not_be_possible_merchant_user_transfer_money_to_common_user() throws Exception {
        // Given
        var commonUserResp = userTestScenario.createCommonUser();
        var merchantUserResp = userTestScenario.createMerchantUser();
        var commonId = userTestScenario.getIdFromResponse(commonUserResp);
        var merchantId = userTestScenario.getIdFromResponse(merchantUserResp);
        userTestScenario.updateBalance(commonId, INITIAL_BALANCE);
        userTestScenario.updateBalance(merchantId, INITIAL_BALANCE);
        testScenario.paymentAllowedByAuthorizer(true);

        // When
        var response = testScenario.executeTransferMoneyRequest(
                AMOUNT_100, merchantId, commonId
        );

        // Then
        var commonWallet = userTestScenario.getWallet(commonId);
        var merchantWallet = userTestScenario.getWallet(merchantId);
        assertThat(commonWallet.get().getBalance()).isEqualByComparingTo(INITIAL_BALANCE);
        assertThat(merchantWallet.get().getBalance()).isEqualByComparingTo(INITIAL_BALANCE);

        var expectedResponse = testScenario
                .expectedErrorResponse(
                "Bad Request",
                "Comerciantes não estão autorizados fazer transferência."
                );
        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.getContentAsString()).isEqualTo(expectedResponse);

        userTestScenario.deleteUserAndWallet(commonId);
        userTestScenario.deleteUserAndWallet(merchantId);
    }

    @Test
    @DisplayName("It should not be possible to transfer if the balance is insufficient.")
    void it_should_not_be_possible_to_transfer_if_the_balance_is_insufficient() throws Exception {
        // Given
        var commonUserResp = userTestScenario.createCommonUser();
        var merchantUserResp = userTestScenario.createMerchantUser();
        var commonId = userTestScenario.getIdFromResponse(commonUserResp);
        var merchantId = userTestScenario.getIdFromResponse(merchantUserResp);
        userTestScenario.updateBalance(commonId, AMOUNT_0);
        userTestScenario.updateBalance(merchantId, INITIAL_BALANCE);
        testScenario.paymentAllowedByAuthorizer(true);

        // When
        var response = testScenario.executeTransferMoneyRequest(
                AMOUNT_100, commonId, merchantId
        );

        // Then
        var commonWallet = userTestScenario.getWallet(commonId);
        var merchantWallet = userTestScenario.getWallet(merchantId);
        assertThat(commonWallet.get().getBalance()).isEqualByComparingTo(AMOUNT_0);
        assertThat(merchantWallet.get().getBalance()).isEqualByComparingTo(INITIAL_BALANCE);

        var expectedResponse = testScenario
                .expectedErrorResponse(
                        "Bad Request",
                        "Saldo insuficiente para realizar a transação."
                );
        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.getContentAsString()).isEqualTo(expectedResponse);

        userTestScenario.deleteUserAndWallet(commonId);
        userTestScenario.deleteUserAndWallet(merchantId);
    }
}