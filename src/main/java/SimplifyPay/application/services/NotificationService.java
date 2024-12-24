package SimplifyPay.application.services;

import SimplifyPay.application.services.implementations.TransferMoneyImpl;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import SimplifyPay.infrastructure.clients.NotificationClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor= @__(@Autowired))
public class NotificationService {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(TransferMoneyImpl.class);
    private final NotificationClient client;

    @Retryable(
        retryFor = FeignException.GatewayTimeout.class,
        maxAttempts = 1,
        backoff = @Backoff(delay = 3000),
        recover= "recoverNotification"
    )
    @Async
    public void sendNotification() { 
        try {
            Thread.sleep(1);
        } catch (InterruptedException ex) {
        }
        client.execute();
        logger.info("Notification send");
    }

    @Recover
    public void recoverNotification(FeignException.GatewayTimeout e) {
        logger.error("Recovering from FeignException.GatewayTimeout: {}", e.getMessage());
        logger.info("Calling another notification provider...");
    }
}
