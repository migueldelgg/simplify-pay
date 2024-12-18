package SimplifyPay.domain.usecases;

import SimplifyPay.application.dtos.TransferMoneyRequest;

public interface TransferMoneyUseCase {
    
    public void execute(TransferMoneyRequest request);

}
