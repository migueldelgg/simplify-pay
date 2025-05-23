package SimplifyPay.application.services.implementations;

import java.util.HashMap;
import java.util.Map;

import SimplifyPay.application.services.strategy.CreateUserStrategy;
import SimplifyPay.application.services.strategy.CreateCommonStrategy;
import SimplifyPay.application.services.strategy.CreateMerchantStrategy;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SimplifyPay.infrastructure.repositories.UserRepository;
import SimplifyPay.infrastructure.repositories.WalletRepository;
import SimplifyPay.application.dtos.CreateUserRequest;
import SimplifyPay.application.dtos.CreateUserResponse;
import SimplifyPay.domain.entities.UserEntity;
import SimplifyPay.domain.entities.WalletEntity;
import SimplifyPay.domain.usecases.CreateUserUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor= @__(@Autowired))
public class CreateUserImpl implements CreateUserUseCase{

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(CreateUserImpl.class);

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    private final Map<Integer, CreateUserStrategy> mapStrategy = Map.of(
            11, new CreateCommonStrategy(),
            14, new CreateMerchantStrategy()
    );

    @Override
    @Transactional
    public Map<String, Object> execute(CreateUserRequest request) {
        var document = request.getDocument().replaceAll("[^0-9]", "");
        var documentLength = document.length();
        request.setDocument(document);

        logger.info("Document from Request: {}", document);
        logger.info("Document lenght: {}", documentLength);

        var resultMap = mapStrategy.get(documentLength).execute(request);

        UserEntity user = (UserEntity) resultMap.get("user");
        WalletEntity wallet = (WalletEntity) resultMap.get("wallet");

        userRepository.save(user);
        walletRepository.save(wallet);

        var response = new CreateUserResponse(
                user.getId(),
                user.getName(), user.getEmail(),
                user.getDocument(), wallet.getType()
        );

        Map<String, Object> map = new HashMap<>();
        map.put("data", response);
        return map;
    }
}
