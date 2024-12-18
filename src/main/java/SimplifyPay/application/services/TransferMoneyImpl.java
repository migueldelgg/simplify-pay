package SimplifyPay.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    
    @Override
    @Transactional
    public void execute(TransferMoneyRequest req) {
        var payeeWallet = walletRepo.findByUserId(req.payeeId());
        Boolean condition;
        
        condition = Validations.isWalletTypeCommon(payeeWallet.getType().toString());
        condition = Validations.isSufficientBalance(payeeWallet, payeeWallet.getBalance());
        if(condition == false) {
            System.out.println("EXCEPTION LANCADA!");
        }
    }
    
}
