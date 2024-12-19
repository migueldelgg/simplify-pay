package SimplifyPay.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SimplifyPay.adapters.clients.AuthorizationClient;
import SimplifyPay.adapters.repositories.TransactionRepository;
import SimplifyPay.adapters.repositories.WalletRepository;
import SimplifyPay.application.dtos.TransferMoneyRequest;
import SimplifyPay.application.validations.Validations;
import SimplifyPay.domain.usecases.TransferMoneyUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor(onConstructor= @__(@Autowired))
public class TransferMoneyImpl implements TransferMoneyUseCase {

    private final WalletRepository walletRepo;
    private final TransactionRepository transactionRepo;
    private final AuthorizationClient client;

    
    @Override
    @Transactional
    public void execute(TransferMoneyRequest req) {
        var payerWallet = walletRepo.findByUserId(req.payerId());
        System.out.println("TIPO DE CARTEIRA: "+payerWallet.getType());
        Validations.isWalletTypeCommon(payerWallet.getType().toString());
        //Validations.isSufficientBalance(payeeWallet, req.value());
        client.execute();
    }
    
}
