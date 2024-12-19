package SimplifyPay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import SimplifyPay.exception.customExceptions.InsufficientBalanceException;
import SimplifyPay.exception.customExceptions.MerchantCannotTransferException;
import feign.FeignException;

@ControllerAdvice
public class RestExceptionHandler {
    
    @ExceptionHandler
    public ResponseEntity<RestErrorMessage> handleFeignException(
        FeignException ex
        ) {
            var customMessage = "Autorização negada.";
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
}
