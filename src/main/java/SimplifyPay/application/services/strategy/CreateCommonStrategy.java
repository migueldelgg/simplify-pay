package SimplifyPay.application.services.strategy;

import SimplifyPay.application.services.CreateUserStrategy;

import java.util.Map;

public class CreateCommonUserStrategy implements CreateUserStrategy {

    @Override
    public Map<String, Object[]> validateDocument(String document) {
        return null;
    }
    
}
