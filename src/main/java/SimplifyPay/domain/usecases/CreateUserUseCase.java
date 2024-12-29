package SimplifyPay.domain.usecases;

import java.util.Map;

import SimplifyPay.application.dtos.CreateUserRequest;

public interface CreateUserUseCase {
    
    Map<String, Object> execute(CreateUserRequest request);
}
