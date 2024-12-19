package SimplifyPay.exception.customExceptions;

public class MerchantCannotTransferException extends RuntimeException{
    public MerchantCannotTransferException() {
        super();
    }

    public MerchantCannotTransferException(String message) {
        super(message); 
    }
}
