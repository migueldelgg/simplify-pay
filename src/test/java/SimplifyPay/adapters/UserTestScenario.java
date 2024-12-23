package SimplifyPay.adapters;

import SimplifyPay.adapters.repositories.TransactionRepositoryTest;
import SimplifyPay.adapters.repositories.UserRepositoryTest;
import SimplifyPay.adapters.repositories.WalletRepositoryTest;
import SimplifyPay.application.dtos.CreateUserData;
import SimplifyPay.application.dtos.ResponseUserData;
import SimplifyPay.domain.entities.WalletEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
public class UserTestScenario {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<CreateUserData> createUserJson;

    @Autowired
    private UserRepositoryTest userRepository;

    @Autowired
    private WalletRepositoryTest walletRepository;

    @Autowired
    private TransactionRepositoryTest transactionRepository;

    public MockHttpServletResponse createCommonUser() throws Exception {
        var request = new CreateUserData("CommonUser",
                "019.327.760-36",
                "common@gmail.com",
                "123"
        );
        return mvc.perform(post("/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createUserJson.write(request).getJson()))
                .andReturn()
                .getResponse();
    }

    public MockHttpServletResponse createMerchantUser() throws Exception {
        var request = new CreateUserData("MerchantUser",
                "43.674.835/0001-30",
                "merchant@gmail.com",
                "123"
        );
        return mvc.perform(post("/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createUserJson.write(request).getJson()))
                .andReturn()
                .getResponse();
    }

    public Integer getIdFromResponse(MockHttpServletResponse response) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseUserData responseData = objectMapper.readValue(response.getContentAsString(), ResponseUserData.class);
        return responseData.getCreateUserResponse().id();
    }

    public void deleteUserAndWallet(Integer id) {
        walletRepository.deleteByUserId(id);
        userRepository.deleteByUserId(id);
    }

    public void updateBalance(Integer id, BigDecimal value) {
        walletRepository.setBalance(id, value);
    }

    public Optional<WalletEntity> getWallet(Integer id) {
        return walletRepository.getByUserId(id);
    }

    public void deleteTransactionByWalletPayerId(UUID id) {
        transactionRepository.deleteByPayerWalletId(id);
    }
}
