package SimplifyPay.application.validations;

import java.math.BigDecimal;

import SimplifyPay.domain.entities.WalletEntity;
import SimplifyPay.domain.entities.WalletType;
import SimplifyPay.exception.customExceptions.InsufficientBalanceException;
import SimplifyPay.exception.customExceptions.MerchantCannotTransferException;

public class Validations {
    
    public static void isWalletTypeCommon(String walletType) {
        if(!walletType.equals(WalletType.COMMON.toString())) {
            throw new MerchantCannotTransferException(
                "Comerciantes não estão autorizados fazer transferência."
            );
        }
    }

    public static void isSufficientBalance(WalletEntity payeeWallet, BigDecimal transferValue) {
        var balance = payeeWallet.getBalance();
        String message = String.format(
    "Saldo insuficiente para realizar a transação. Saldo: %s, Deseja transferir: %s", 
            payeeWallet.getBalance(), transferValue);
        if (balance.compareTo( BigDecimal.ZERO ) <= 0 || balance.compareTo( transferValue ) < 0) {
            throw new InsufficientBalanceException( message );
        }    
    }

}
