package SimplifyPay.domain.usecases;

import SimplifyPay.application.dtos.TransferMoneyRequest;
import SimplifyPay.application.dtos.TransferMoneyResponse;

public interface TransferMoneyUseCase {

    TransferMoneyResponse execute(TransferMoneyRequest request);

}
