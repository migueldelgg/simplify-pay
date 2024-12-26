package SimplifyPay.infrastructure;

import SimplifyPay.infrastructure.clients.response.Authorize;
import SimplifyPay.infrastructure.clients.response.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class AuthorizationClientTest {

    @Autowired
    private static JacksonTester<Authorize> authorizeJacksonTester;

    public static void stubAuthorizationSuccess() throws Exception {
        var json = authorizeJacksonTester.write(new Authorize("success", new Data(true))).getJson();

        System.out.println("Stubbing /authorize for success");
        stubFor(get(urlEqualTo("/authorize"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(json)));
    }

    public static void stubAuthorizationForbidden() throws Exception {
        var json = authorizeJacksonTester.write(new Authorize("fail", new Data(false))).getJson();

        System.out.println("Stubbing /authorize for forbidden");
        stubFor(get(urlEqualTo("/authorize"))
                .willReturn(aResponse()
                        .withStatus(403)
                        .withHeader("Content-Type", "application/json")
                        .withBody(json)));
    }
}
