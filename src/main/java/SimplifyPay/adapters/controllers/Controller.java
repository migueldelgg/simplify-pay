package SimplifyPay.adapters.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SimplifyPay.application.dtos.CreateUserData;
import SimplifyPay.application.services.CreateUserImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/v1/")
@RequiredArgsConstructor(onConstructor= @__(@Autowired))
public class Controller {

    private final CreateUserImpl createUserImpl;
    
    @PostMapping("user/")
    public ResponseEntity<Map<String, Object>> createUserAndWallet(@RequestBody @Valid CreateUserData input) {
        var response = createUserImpl.execute(input);
    
        return ResponseEntity.ok().body(response);
    }
    
}
