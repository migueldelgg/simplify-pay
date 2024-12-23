package SimplifyPay.application.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import SimplifyPay.domain.entities.WalletType;

public record CreateUserResponse (
    Integer id,
    String name, 
    String email, 
    String document, 
    @JsonProperty(value="wallet_type") WalletType walletType
){}
