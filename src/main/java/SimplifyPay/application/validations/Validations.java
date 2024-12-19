package SimplifyPay.application.validations;

import java.math.BigDecimal;
import java.util.Optional;

import SimplifyPay.domain.entities.WalletEntity;
import SimplifyPay.domain.entities.WalletType;
import SimplifyPay.exception.customExceptions.InsufficientBalanceException;
import SimplifyPay.exception.customExceptions.MerchantCannotTransferException;
import SimplifyPay.exception.customExceptions.PayeeNotFound;
import SimplifyPay.exception.customExceptions.PayerEqualsToPayeeException;
import SimplifyPay.exception.customExceptions.PayerNotFound;

public class Validations {

    public static void isPayerEqualToReceiver(Integer payerId, Integer payeeId) {
        if(payerId.equals(payeeId)) {
            throw new PayerEqualsToPayeeException(
                "Pagador deve ser diferente do recebidor."
            );
        }        
    }

    public static void userExist(
        Optional<WalletEntity> payerWallet, Optional<WalletEntity> payeeWallet
    ) {
        if(!payerWallet.isPresent()) {
            throw new PayerNotFound(
                "Pagador não encontrado."
            );
        }
        if(!payeeWallet.isPresent()) {
            throw new PayeeNotFound(
                "Recebidor não encontrado."
            );
        }
    }
    
    public static void isWalletTypeCommon(Optional<WalletEntity> payerWallet) {
        var walletType = payerWallet.get().getType().toString();
        if(!walletType.equals(WalletType.COMMON.toString())) {
            throw new MerchantCannotTransferException(
                "Comerciantes não estão autorizados fazer transferência."
            );
        }
    }

    public static void isSufficientBalance(
        Optional<WalletEntity> payerWallet, BigDecimal transferValue
    ) {
        var balance = payerWallet.get().getBalance();
        String message = String.format(
        "Saldo insuficiente para realizar a transação. Saldo: %s, Deseja transferir: %s", 
            balance, transferValue);
        if (balance.compareTo( BigDecimal.ZERO ) <= 0 || balance.compareTo( transferValue ) < 0) {
            throw new InsufficientBalanceException( message );
        }    
    }
}
