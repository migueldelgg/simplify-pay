package SimplifyPay.application.dtos;

public record CreateUserData (
    String name, String document, 
    String email, String password,
    String walletType
){}
