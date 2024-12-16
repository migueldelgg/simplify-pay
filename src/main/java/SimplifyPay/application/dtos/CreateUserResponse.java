package SimplifyPay.application.dtos;

import SimplifyPay.domain.entities.WalletType;

public record CreateUserResponse (
    String name, String email, 
    String document, WalletType walletType
){}
