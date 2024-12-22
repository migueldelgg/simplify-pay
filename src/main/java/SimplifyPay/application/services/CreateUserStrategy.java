package SimplifyPay.application.services;

import SimplifyPay.application.dtos.CreateUserData;

import java.util.Map;

public interface CreateUserStrategy {
    Map<String, Object> execute(CreateUserData request);
}
