package SimplifyPay.application.validations;

import java.math.BigDecimal;

import SimplifyPay.domain.entities.WalletEntity;
import SimplifyPay.domain.entities.WalletType;

public class Validations {
    
    public static Boolean isWalletTypeCommon(String walletType) {
        return walletType.equals(WalletType.COMMON.name());
    }

    public static Boolean isSufficientBalance(WalletEntity payeeWallet, BigDecimal value) {
        var balance = payeeWallet.getBalance();
        var zero = new BigDecimal(0.00);
        return !(balance.compareTo(zero) <= 0 || balance.compareTo(value) < 0);
    }

}
