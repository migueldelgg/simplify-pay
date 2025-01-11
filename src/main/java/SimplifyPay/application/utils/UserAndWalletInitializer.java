package SimplifyPay.application.utils;

import SimplifyPay.application.dtos.CreateUserRequest;
import SimplifyPay.domain.entities.UserEntity;
import SimplifyPay.domain.entities.WalletEntity;
import SimplifyPay.domain.entities.WalletType;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserAndWalletInitializer {

    public static Map<String, Object> execute(CreateUserRequest request, WalletType type) {
        var user = UserEntity.builder()
                .name(request.getName())
                .document(request.getDocument())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        var wallet = WalletEntity.builder()
                .id(UUID.randomUUID())
                .user(user)
                .type(type)
                .balance(BigDecimal.valueOf(100.00))
                .build();

        user.setWallet(wallet);
        wallet.setUser(user);

        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("wallet", wallet);

        return map;
    }
}
