package SimplifyPay.application.services.strategy;

import SimplifyPay.application.dtos.CreateUserRequest;
import SimplifyPay.application.utils.UserAndWalletInitializer;
import SimplifyPay.domain.entities.WalletType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CreateMerchantStrategy implements CreateUserStrategy {
    @Override
    public Map<String, Object> execute(CreateUserRequest request) {
        return UserAndWalletInitializer.execute(request, WalletType.MERCHANT);
    }
}
