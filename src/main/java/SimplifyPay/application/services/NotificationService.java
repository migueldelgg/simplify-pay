package SimplifyPay.application.services;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import SimplifyPay.adapters.clients.NotificationClient;
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
    public void sendNotification() { 
        client.execute();
        logger.info("Notification send");
    }

    @Recover
    public void recoverNotification(
        FeignException.GatewayTimeout e,
         NotificationClient notifyClient
    ) {
        logger.error(e.getMessage());
        logger.info("Calling another notification provider");
    }
}
