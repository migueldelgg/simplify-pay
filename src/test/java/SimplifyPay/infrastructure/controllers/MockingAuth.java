package SimplifyPay.infrastructure.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

@EnableWireMock({@ConfigureWireMock(name = "auth_mock", port = 9000)})
public class MockingAuth {

    @Value("${wiremock.server.baseUrl}")
    private String wireMockUrl;

    public static void stubAuthorizationSuccess() throws Exception {
        //var json = authorizeJacksonTester.write(new Authorize("success", new Data(true))).getJson();

        System.out.println("Stubbing /authorize for success");
        stubFor(get(urlEqualTo("/authorize"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"status\":\"success\",\"data\":{\"authorized\":true}}")));
    }

    public static void stubAuthorizationForbidden() throws Exception {
        //var json = authorizeJacksonTester.write(new Authorize("fail", new Data(false))).getJson();

        System.out.println("Stubbing /authorize for forbidden");
        stubFor(get(urlEqualTo("/authorize"))
                .willReturn(aResponse()
                        .withStatus(403)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"status\":\"fail\",\"data\":{\"authorized\":false}}")));
    }
}
