package SimplifyPay.adapters.controllers;

import SimplifyPay.adapters.clients.AuthorizationClient;
import SimplifyPay.adapters.clients.response.Authorize;
import SimplifyPay.adapters.clients.response.Data;
import SimplifyPay.application.dtos.TransferMoneyRequest;
import SimplifyPay.application.services.TransferMoneyImpl;
import SimplifyPay.exception.RestErrorMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
class ControllerTest {

    final Integer COMMON_USER_ID = 2;
    final Integer MERCHANT_USER_ID = 38;
    final Integer INVALID_USER_ID = 0;
    final BigDecimal AMOUNT_100 = new BigDecimal("100.00");
    final BigDecimal AMOUNT_150 = new BigDecimal("50.00");
    final BigDecimal AMOUNT_0 = new BigDecimal("0.00");
    final Data DATA_AUTHORIZATION_TRUE = new Data(true);
    final Authorize AUTHORIZE_TRUE = new Authorize("sucess", DATA_AUTHORIZATION_TRUE);

    @MockBean
    private AuthorizationClient authorizationClient;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<TransferMoneyRequest> transferMoneyJson;

    //@MockBean
    @Autowired
    private TransferMoneyImpl transferService;

    @Test
    @DisplayName("Merchant cant do it transfer money.")
    void should_not_be_possible_merchant_user_transfer_money_to_common_user() throws Exception {
        //Mockando autorizacao
        var authorizedResponse = new Authorize("sucess", DATA_AUTHORIZATION_TRUE);
        Mockito.when(authorizationClient.execute()).thenReturn(authorizedResponse);

        // montada request para transfer
        var request = new TransferMoneyRequest(AMOUNT_100, MERCHANT_USER_ID, COMMON_USER_ID);

        var response = mvc.perform(post("/v1/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transferMoneyJson.write(request).getJson()))
                .andReturn()
                .getResponse();

        var errorMessage = new RestErrorMessage(
                "Bad Request",
                "Comerciantes não estão autorizados fazer transferência."
        );
        var objectMapper = new ObjectMapper();
        String expectedResponse = objectMapper.writeValueAsString(errorMessage);

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.getContentAsString()).isEqualTo(expectedResponse);
    }

}