package SimplifyPay.infrastructure.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import SimplifyPay.infrastructure.clients.response.Authorize;

@FeignClient(
    value = "authorization-dev-tools", 
    url="${authorization-client.base-url}"
)
public interface AuthorizationClient {
    
    @GetMapping("/authorize")
    Authorize execute();
}
