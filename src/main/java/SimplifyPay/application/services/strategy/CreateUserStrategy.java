package SimplifyPay.application.services.strategy;

import SimplifyPay.application.dtos.CreateUserRequest;

import java.util.Map;

public interface CreateUserStrategy {
    Map<String, Object> execute(CreateUserRequest request);
}
