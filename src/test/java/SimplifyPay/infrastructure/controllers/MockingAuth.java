package SimplifyPay.infrastructure.controllers;

import SimplifyPay.infrastructure.TestConfig;
import SimplifyPay.infrastructure.clients.AuthorizationClient;
import SimplifyPay.infrastructure.clients.response.Authorize;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.annotation.*;

import static SimplifyPay.infrastructure.AuthorizationClientTest.stubAuthorizationSuccess;
import static SimplifyPay.infrastructure.AuthorizationClientTest.stubAuthorizationForbidden;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest()
@AutoConfigureWireMock(port = 8080)
@ExtendWith(
        {
                WireMockExtension.class,
                SpringExtension.class,
        }
)
public class MockingAuth {

    @Autowired
    private AuthorizationClient authorizationClient;

    @Test
    public void shouldReturnSuccessResponse() throws Exception {
        stubAuthorizationSuccess();

        Authorize response = authorizationClient.execute();
        assertThat(response.status()).isEqualTo("success");
        assertThat(response.data().authorization()).isTrue();
    }

    @Test
    public void shouldReturnForbiddenResponse() throws Exception {
        stubAuthorizationForbidden();

        Authorize response = authorizationClient.execute();
        assertThat(response.status()).isEqualTo("fail");
        assertThat(response.data().authorization()).isFalse();
    }
}
