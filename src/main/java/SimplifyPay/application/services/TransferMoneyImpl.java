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
    private final AuthorizationClient authClient;
  
    @Override
    @Transactional
    public void execute(TransferMoneyRequest req) {
        authClient.execute();
        Validations.isPayerEqualToReceiver(req.payerId(), req.payeeId()); 

        var payerWallet = walletRepo.findByUserId(req.payerId());
        var payeeWallet = walletRepo.findByUserId(req.payeeId());

        Validations.userExist(payerWallet, payeeWallet);
        Validations.isWalletTypeCommon(payerWallet);

        System.out.println("TIPO DE CARTEIRA: "+payerWallet.get().getType());
        Validations.isSufficientBalance(payerWallet, req.value());
    }
}
