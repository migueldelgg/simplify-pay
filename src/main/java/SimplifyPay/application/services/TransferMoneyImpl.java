package SimplifyPay.application.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SimplifyPay.adapters.clients.AuthorizationClient;
import SimplifyPay.adapters.repositories.TransactionRepository;
import SimplifyPay.adapters.repositories.WalletRepository;
import SimplifyPay.application.dtos.TransferMoneyRequest;
import SimplifyPay.application.validations.Validations;
import SimplifyPay.domain.entities.TransactionEntity;
import SimplifyPay.domain.entities.WalletEntity;
import SimplifyPay.domain.usecases.TransferMoneyUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor(onConstructor= @__(@Autowired))
public class TransferMoneyImpl implements TransferMoneyUseCase {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(TransferMoneyImpl.class);

    private final WalletRepository walletRepo;
    private final TransactionRepository transactionRepo;
    private final AuthorizationClient authClient;
  
    @Override
    @Transactional
    public void execute(TransferMoneyRequest req) {
        logger.info(req.toString());
        Validations.isPayerEqualToReceiver(req.payerId(), req.payeeId()); 
        logger.info("Request validated.");

        logger.info("Searching the database...");

        var payerWallet = walletRepo.findByUserId(req.payerId());
        var payeeWallet = walletRepo.findByUserId(req.payeeId());

        logger.info(
            "Wallets founded in database \n " 
            + payerWallet.get().toString() + "\n"
            + payeeWallet.get().toString()
        );

        Validations.userExist(payerWallet, payeeWallet);
        Validations.isWalletTypeCommon(payerWallet);
        Validations.isSufficientBalance(payerWallet, req.value());
        logger.info("Data validated");

        authClient.execute();
        logger.info("External service authorized");

        makePayment(payerWallet.get(), payeeWallet.get(), req.value());
        logger.info("Payment made");

        createTransaction(payerWallet.get(), payeeWallet.get(), req.value());
        logger.info("Transaction Created");
    }

    public void createTransaction(
        WalletEntity payer, WalletEntity payee, BigDecimal amount
        ) {
            var transaction = TransactionEntity.builder()
                .payerWallet(payer)
                .payeeWallet(payee)
                .amount(amount)
                .processedAt(LocalDateTime.now())
                .build();  
            transactionRepo.save(transaction);
    }

    public void makePayment(
        WalletEntity payerWallet, WalletEntity payeeWallet, BigDecimal amount
        ) {
            payeeWallet.setBalance(payeeWallet.getBalance().add(amount));
            payerWallet.setBalance(payerWallet.getBalance().subtract(amount));

            walletRepo.save(payeeWallet);
            walletRepo.save(payerWallet);
        }
}
