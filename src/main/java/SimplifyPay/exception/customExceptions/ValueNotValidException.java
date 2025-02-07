package SimplifyPay.exception.customExceptions;

public class ValueNotValidException extends RuntimeException{
  public ValueNotValidException() {
    super();
  }

  public ValueNotValidException(String message) {
    super(message);
  }
}
