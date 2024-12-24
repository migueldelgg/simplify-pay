package SimplifyPay.exception.customExceptions;

public class PayerEqualsToPayeeException extends RuntimeException{
    public PayerEqualsToPayeeException() {
        super();
    }

    public PayerEqualsToPayeeException(String message) {
        super(message); 
    }
}
