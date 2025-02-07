package SimplifyPay.application.utils;

import java.math.BigDecimal;
import java.util.Optional;

import SimplifyPay.domain.entities.WalletEntity;
import SimplifyPay.domain.entities.WalletType;
import SimplifyPay.exception.customExceptions.InsufficientBalanceException;
import SimplifyPay.exception.customExceptions.MerchantCannotTransferException;
import SimplifyPay.exception.customExceptions.PayeeNotFound;
import SimplifyPay.exception.customExceptions.PayerEqualsToPayeeException;
import SimplifyPay.exception.customExceptions.PayerNotFound;
import SimplifyPay.exception.customExceptions.ValueNotValidException;

import org.slf4j.Logger;

public class Validations {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Validations.class);

    public static void isPayerEqualToReceiver(Integer payerId, Integer payeeId) {
        logger.info("Verificando se os Ids passados na requisição são iguais.");
        if(payerId.equals(payeeId)) {
            throw new PayerEqualsToPayeeException(
                "Pagador deve ser diferente do recebidor."
            );
        }
        logger.info("Ids diferentes, essa validação terminou.");
    }

    public static void userExist(
        Optional<WalletEntity> payerWallet, Optional<WalletEntity> payeeWallet
    ) {
        logger.info("Verificando se os usuarios existem no banco de dados.");

        if(payerWallet.isEmpty()) {
            throw new PayerNotFound(
                "Pagador não encontrado."
            );
        }

        logger.info("Pagador encontrado, verificando recebidor.");

        if(payeeWallet.isEmpty()) {
            throw new PayeeNotFound(
                "Recebidor não encontrado."
            );
        }

        logger.info("Recebidor encontrado, essa validação terminou.");
    }
    
    public static void isWalletTypeCommon(Optional<WalletEntity> payerWallet) {
        logger.info("Verificando se a carteira é válida para transferência.");

        var walletType = payerWallet.get().getType().toString();
        if(!walletType.equals(WalletType.COMMON.toString())) {
            logger.info("Carteira inválida para transferência.");
            throw new MerchantCannotTransferException(
                "Comerciantes não estão autorizados fazer transferência."
            );
        }
        logger.info("Carteira válida para transferência, essa validação terminou.");
    }

    public static void isSufficientBalance(
        Optional<WalletEntity> payerWallet, BigDecimal transferValue
    ) {
        logger.info("Verificando se o saldo da carteira é suficiente.");
        var balance = payerWallet.get().getBalance();
        String message = "Saldo insuficiente para realizar a transação.";
        if (balance.compareTo( BigDecimal.ZERO ) <= 0 || balance.compareTo( transferValue ) < 0) {
            logger.info("Saldo suficiente, essa validação terminou.");
            throw new InsufficientBalanceException( message );
        }
    }

    public static void isValueNegative(BigDecimal transferValue) {
        logger.info("Verificando se o valor da requisição é válido.");
        if (transferValue == null || transferValue.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValueNotValidException();
        }
    }
}
