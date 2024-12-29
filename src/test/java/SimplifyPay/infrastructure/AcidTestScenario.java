package SimplifyPay.infrastructure;

import SimplifyPay.application.dtos.CreateUserRequest;
import SimplifyPay.infrastructure.repositories.TransactionRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class AcidTestScenario {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<CreateUserRequest> createUserJson;

    @Autowired
    private TransactionRepositoryTest transactionRepository;

    @Autowired
    private TransferMoneyTestScenario transferMoneyTestScenario;

    // ACID TEST
    public void executeTransferMoneyRequestWithThreadSleep(
            BigDecimal value, Integer payerId, Integer payeeId, Integer millis) throws Exception
    {
        Thread.sleep(millis);
        transferMoneyTestScenario.executeTransferMoneyRequest(value, payerId, payeeId);
    }

    public MockHttpServletResponse creatingJohn() throws Exception {
        var request = new CreateUserRequest("John",
                "284.786.530-66",
                "john@gmail.com",
                "123"
        );
        return performCreateUserRequest(request);
    }

    public MockHttpServletResponse creatingMaria() throws Exception {
        var request = new CreateUserRequest("Maria",
                "864.106.220-31",
                "maria@gmail.com",
                "123"
        );
        return performCreateUserRequest(request);
    }

    public MockHttpServletResponse creatingCarla() throws Exception {
        var request = new CreateUserRequest("Carla",
                "731.412.090-06",
                "Carla@gmail.com",
                "123"
        );
        return performCreateUserRequest(request);
    }

    public MockHttpServletResponse performCreateUserRequest(CreateUserRequest request) throws Exception {
        return mvc.perform(post("/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createUserJson.write(request).getJson()))
                .andReturn()
                .getResponse();
    }
}
