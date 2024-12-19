package SimplifyPay.application.services;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SimplifyPay.adapters.repositories.UserRepository;
import SimplifyPay.adapters.repositories.WalletRepository;
import SimplifyPay.application.dtos.CreateUserData;
import SimplifyPay.application.dtos.CreateUserResponse;
import SimplifyPay.domain.entities.UserEntity;
import SimplifyPay.domain.entities.WalletEntity;
import SimplifyPay.domain.entities.WalletType;
import SimplifyPay.domain.usecases.CreateUserUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor= @__(@Autowired))
public class CreateUserImpl implements CreateUserUseCase{

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    @Override
    @Transactional
    public Map<String, Object> execute(CreateUserData request) {

        var user = UserEntity.builder()
            .name(request.name())
            .document(request.document())
            .email(request.email())
            .password(request.password())
            .build();
        
        var wallet = WalletEntity.builder()
            .id(UUID.randomUUID())
            .user(user)
            .type(WalletType.valueOf(request.walletType()))
            .balance(BigDecimal.valueOf(0.00))
            .build();
        
        user.setWallet(wallet);        
        userRepository.save(user);

        wallet.setUser(user);
        walletRepository.save(wallet);

        var response = new CreateUserResponse(
            user.getName(), user.getEmail(),
            user.getDocument(), wallet.getType()
        );

        Map<String, Object> map = new HashMap<>();
        map.put("data", response);
        return map;
    }
    
}
