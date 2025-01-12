package SimplifyPay.infrastructure.scenarios;

import SimplifyPay.application.dtos.TransferMoneyRequest;
import SimplifyPay.application.dtos.TransferMoneyResponse;
import SimplifyPay.exception.RestErrorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class TransferMoneyTestScenario {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<TransferMoneyRequest> transferMoneyJson;

    public MockHttpServletResponse executeTransferMoneyRequest(
            BigDecimal value, Integer payerId, Integer payeeId) throws Exception {
        var request = new TransferMoneyRequest(value, payerId, payeeId);
        return mvc.perform(post("/v1/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transferMoneyJson.write(request).getJson()))
                .andReturn()
                .getResponse();
    }

    public String expectedErrorResponse(
            String reasonPhrase, String expectedMessage) throws JsonProcessingException {
        var errorMessage = new RestErrorMessage(
                reasonPhrase,
                expectedMessage
        );

        var objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(errorMessage);
    }

    public String expectedSuccessResponse(
            UUID payer, UUID payee, BigDecimal value) throws JsonProcessingException {
        var response = new TransferMoneyResponse(
                payer,
                payee,
                value
        );

        var objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(response);
    }
}
