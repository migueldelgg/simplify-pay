package SimplifyPay.adapters.controllers;

import SimplifyPay.application.dtos.TransferMoneyRequest;
import SimplifyPay.application.services.TransferMoneyImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
class ControllerTest {

    final Integer PAYER_USER_ID = 2;
    final Integer PAYEE_USER_ID = 38;
    final Integer INVALID_USER_ID = 0;
    final BigDecimal AMOUNT_10 = new BigDecimal("10.00");
    final BigDecimal AMOUNT_150 = new BigDecimal("50.00");
    final BigDecimal AMOUNT_0 = new BigDecimal("0.00");

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<TransferMoneyRequest> transferMoneyJson;

    @MockBean
    private TransferMoneyImpl transferService;

    @Test
    @DisplayName("Should throw bad request exception if don't have a body.")
    void transferMoney_scneario1() throws Exception {

        var response = mvc.perform(post("/v1/transfer"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Should return 200 if the request is valid.")
    void transferMoney_scneario2() throws Exception {
        var request = new TransferMoneyRequest(AMOUNT_10, PAYER_USER_ID, PAYEE_USER_ID);

        doNothing().when(transferService).execute(request);

        var response = mvc.perform(post("/v1/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transferMoneyJson.write(request).getJson()))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(204);
        assertThat(response.getContentAsString()).isEmpty();
    }
}