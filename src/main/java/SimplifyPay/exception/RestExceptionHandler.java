package SimplifyPay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import SimplifyPay.exception.customExceptions.InsufficientBalanceException;
import SimplifyPay.exception.customExceptions.MerchantCannotTransferException;
import SimplifyPay.exception.customExceptions.PayeeNotFound;
import SimplifyPay.exception.customExceptions.PayerEqualsToPayeeException;
import SimplifyPay.exception.customExceptions.PayerNotFound;
import feign.FeignException;

import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<RestErrorMessage> handleException(
            Exception ex
    ) {
        var code = HttpStatus.INTERNAL_SERVER_ERROR;
        var response = RestErrorMessage.builder()
                .statusCode(code.getReasonPhrase())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(code).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<RestErrorMessage> handleFeignException(
        FeignException ex
        ) {
            var customMessage = "Pagamento não autorizado.";
            var code = HttpStatus.FORBIDDEN;
            var response = RestErrorMessage.builder()
                .statusCode(code.getReasonPhrase())
                .message(customMessage)
                .build();
            return ResponseEntity.status(code).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<RestErrorMessage> handleInsufficientBalanceException(
        InsufficientBalanceException ex
        ) {
            var code = HttpStatus.BAD_REQUEST;
            var response = RestErrorMessage.builder()
                .statusCode(code.getReasonPhrase())
                .message(ex.getMessage())
                .build();
            return ResponseEntity.status(code).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<RestErrorMessage> handleMerchantCannotTransferException(
        MerchantCannotTransferException ex
        ) {
            var code = HttpStatus.BAD_REQUEST;
            var response = RestErrorMessage.builder()
                .statusCode(code.getReasonPhrase())
                .message(ex.getMessage())
                .build();
            return ResponseEntity.status(code).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<RestErrorMessage> handlePayerEqualsToPayeeException(
        PayerEqualsToPayeeException ex
        ) {
            var code = HttpStatus.BAD_REQUEST;
            var response = RestErrorMessage.builder()
                .statusCode(code.getReasonPhrase())
                .message(ex.getMessage())
                .build();
            return ResponseEntity.status(code).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<RestErrorMessage> handlePayerNotFound(
        PayerNotFound ex
        ) {
            var code = HttpStatus.BAD_REQUEST;
            var response = RestErrorMessage.builder()
                .statusCode(code.getReasonPhrase())
                .message(ex.getMessage())
                .build();
            return ResponseEntity.status(code).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<RestErrorMessage> handlePayeeNotFound(
        PayeeNotFound ex
        ) {
            var code = HttpStatus.BAD_REQUEST;
            var response = RestErrorMessage.builder()
                .statusCode(code.getReasonPhrase())
                .message(ex.getMessage())
                .build();
            return ResponseEntity.status(code).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<RestErrorMessage> handlePayeeNotFound(
        FeignException.GatewayTimeout ex
        ) {
            var code = HttpStatus.SERVICE_UNAVAILABLE;
            var response = RestErrorMessage.builder()
                .statusCode(code.getReasonPhrase())
                .message(ex.getMessage())
                .build();
            return ResponseEntity.status(code).body(response);
    }


    @ExceptionHandler
    public ResponseEntity<RestErrorMessage> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex
    ) {
        var code = HttpStatus.CONFLICT;
        var fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> Map.of(
                        "field", error.getField(),
                        "rejectedValue", error.getRejectedValue(),
                        "message", error.getDefaultMessage()
                ))
                .toList();

        var response = RestErrorMessage.builder()
                .statusCode(code.getReasonPhrase())
                .message("Validation error: \n"+ fieldErrors)
                .build();

        return ResponseEntity.status(code).body(response);
    }

}
