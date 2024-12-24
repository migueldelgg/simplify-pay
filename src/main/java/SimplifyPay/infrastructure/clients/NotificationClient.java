package SimplifyPay.infrastructure.clients;

import java.util.concurrent.CompletableFuture;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
    value = "notification-dev-tools", 
    url="https://util.devi.tools/api/v1"
)
public interface NotificationClient {
    
    @PostMapping("/notify")
    @Async
    CompletableFuture<ResponseEntity<Void>> execute();
}
