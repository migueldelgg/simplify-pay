package SimplifyPay.infrastructure.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import SimplifyPay.infrastructure.clients.response.Authorize;

@FeignClient(
    value = "authorization-dev-tools", 
    url="https://util.devi.tools/api/v2"
)
public interface AuthorizationClient {
    
    @GetMapping("/authorize")
    Authorize execute();
}
