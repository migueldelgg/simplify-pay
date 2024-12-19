package SimplifyPay.exception.customExceptions;

public class PayerNotFound extends RuntimeException{
    public PayerNotFound() {
        super();
    }

    public PayerNotFound(String message) {
        super(message); 
    }
}
