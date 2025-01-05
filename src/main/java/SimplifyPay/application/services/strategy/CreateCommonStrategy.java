package SimplifyPay.application.services.strategy;

import SimplifyPay.application.dtos.CreateUserRequest;
import SimplifyPay.application.utils.UserAndWalletInitializer;
import SimplifyPay.domain.entities.WalletType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor= @__(@Autowired))
public class CreateCommonStrategy implements CreateUserStrategy {
    @Override
    public Map<String, Object> execute(CreateUserRequest request) {
        return UserAndWalletInitializer.execute(request, WalletType.COMMON);
    }
}
