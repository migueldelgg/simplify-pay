package SimplifyPay.infrastructure.controllers;

import java.util.Map;

import SimplifyPay.application.dtos.TransferMoneyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SimplifyPay.application.dtos.CreateUserRequest;
import SimplifyPay.application.dtos.TransferMoneyRequest;
import SimplifyPay.application.services.implementations.CreateUserImpl;
import SimplifyPay.application.services.implementations.TransferMoneyImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1")
@EnableAsync
@EnableRetry
@RequiredArgsConstructor(onConstructor= @__(@Autowired))
public class Controller {

    private final CreateUserImpl createUserImpl;
    private final TransferMoneyImpl transferMoney;
    
    @PostMapping("/user")
    public ResponseEntity<Map<String, Object>> createUserAndWallet(@RequestBody @Valid CreateUserRequest input) {
        var response = createUserImpl.execute(input);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferMoneyResponse> transferMoney(@RequestBody TransferMoneyRequest req) {
        var response = transferMoney.execute(req);
        return ResponseEntity.ok().body(response);
    }
}
