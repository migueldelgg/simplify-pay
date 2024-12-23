package SimplifyPay.adapters;

import SimplifyPay.adapters.clients.AuthorizationClient;
import SimplifyPay.adapters.clients.response.Authorize;
import SimplifyPay.adapters.clients.response.Data;
import SimplifyPay.application.dtos.ResponseUserData;
import SimplifyPay.application.dtos.TransferMoneyRequest;
import SimplifyPay.exception.RestErrorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
public class TransferMoneyTestScenario {

    @MockBean
    private AuthorizationClient authorizationClient;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<TransferMoneyRequest> transferMoneyJson;

    public void paymentAllowedByAuthorizer(Boolean value) {
        var data = new Data(value);
        if(value) {
            var authorizedResponse = new Authorize("sucess", data);
            Mockito.when(authorizationClient.execute()).thenReturn(authorizedResponse);
        }
        var authorizedResponse = new Authorize("fail", data);
        Mockito.when(authorizationClient.execute()).thenReturn(authorizedResponse);
    }

    public MockHttpServletResponse executeTransferMoneyRequest(
            BigDecimal value, Integer payerId, Integer payeeId) throws Exception
        {
        var request = new TransferMoneyRequest(value, payerId, payeeId);
        return mvc.perform(post("/v1/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transferMoneyJson.write(request).getJson()))
                        .andReturn()
                        .getResponse();
    }

    public String expectedErrorResponse(
            String reasonPhrase, String expectedMessage) throws JsonProcessingException
    {
        var errorMessage = new RestErrorMessage(
                reasonPhrase,
                expectedMessage);

        var objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(errorMessage);
    }

}
