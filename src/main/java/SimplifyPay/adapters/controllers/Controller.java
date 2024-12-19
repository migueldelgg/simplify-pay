package SimplifyPay.adapters.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SimplifyPay.application.dtos.CreateUserData;
import SimplifyPay.application.dtos.TransferMoneyRequest;
import SimplifyPay.application.services.CreateUserImpl;
import SimplifyPay.application.services.TransferMoneyImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/v1/")
@RequiredArgsConstructor(onConstructor= @__(@Autowired))
public class Controller {

    private final CreateUserImpl createUserImpl;
    private final TransferMoneyImpl transferMoney;
    
    @PostMapping("user/")
    public ResponseEntity<Map<String, Object>> createUserAndWallet(@RequestBody @Valid CreateUserData input) {
        var response = createUserImpl.execute(input);
    
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("transfer-money/")
    public ResponseEntity<String> transferMoneyMethod(@RequestBody TransferMoneyRequest req) {
        System.out.println("VALOR A SER TRANSFERIDO CONTROLLER: "+ req.value());

        transferMoney.execute(req);
        
        var resposta = "deu bom!";

        return ResponseEntity.ok().body(resposta);
    }
    
}
