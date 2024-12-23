package SimplifyPay.adapters.controllers;

import SimplifyPay.adapters.CreateUserTestScenario;
import SimplifyPay.adapters.TransferMoneyTestScenario;
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

    @Autowired
    private TransferMoneyTestScenario testScenario;

    @Autowired
    private CreateUserTestScenario userTestScenario;

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
        userTestScenario.updateBalance(commonId);
        userTestScenario.updateBalance(merchantId);
        testScenario.paymentAllowedByAuthorizer(true);

        // When
        var response = testScenario.executeTransferMoneyRequest(
                AMOUNT_100, merchantId, commonId
        );

        // Then
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
}