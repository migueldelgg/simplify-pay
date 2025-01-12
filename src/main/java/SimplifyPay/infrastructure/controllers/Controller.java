package SimplifyPay.infrastructure.controllers;

import java.util.Map;

import SimplifyPay.application.dtos.TransferMoneyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user")
    public Map<String, Object> createUserAndWallet(@RequestBody @Valid CreateUserRequest input) {
        return createUserImpl.execute(input);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/transfer")
    public TransferMoneyResponse transferMoney(@RequestBody TransferMoneyRequest req) {
        return transferMoney.execute(req);
    }
}
