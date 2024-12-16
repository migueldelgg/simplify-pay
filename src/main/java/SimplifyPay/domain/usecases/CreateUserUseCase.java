package SimplifyPay.domain.usecases;

import java.util.Map;

import SimplifyPay.application.dtos.CreateUserData;

public interface CreateUserUseCase {
    
    public Map<String, Object> execute(CreateUserData data);

}
