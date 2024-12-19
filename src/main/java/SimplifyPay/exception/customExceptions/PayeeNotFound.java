package SimplifyPay.exception.customExceptions;

public class PayeeNotFound extends RuntimeException{
    public PayeeNotFound() {
        super();
    }

    public PayeeNotFound(String message) {
        super(message); 
    }
}
