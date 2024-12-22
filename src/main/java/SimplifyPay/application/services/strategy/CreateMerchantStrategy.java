package SimplifyPay.application.services.strategy;

import SimplifyPay.adapters.repositories.UserRepository;
import SimplifyPay.adapters.repositories.WalletRepository;
import SimplifyPay.application.dtos.CreateUserData;
import SimplifyPay.application.dtos.CreateUserResponse;
import SimplifyPay.application.services.CreateUserStrategy;
import SimplifyPay.domain.entities.UserEntity;
import SimplifyPay.domain.entities.WalletEntity;
import SimplifyPay.domain.entities.WalletType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CreateMerchantStrategy implements CreateUserStrategy {

    @Override
    public Map<String, Object> execute(CreateUserData request) {
        var user = UserEntity.builder()
                .name(request.getName())
                .document(request.getDocument())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        var wallet = WalletEntity.builder()
                .id(UUID.randomUUID())
                .user(user)
                .type(WalletType.MERCHANT)
                .balance(BigDecimal.valueOf(0.00))
                .build();

        user.setWallet(wallet);
        wallet.setUser(user);

        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("wallet", wallet);
        return map;
    }
    
}
