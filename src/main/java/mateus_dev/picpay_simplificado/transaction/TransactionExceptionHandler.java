package mateus_dev.picpay_simplificado.transaction;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TransactionExceptionHandler {
  @ExceptionHandler
  public ResponseEntity<Object> handleTransactionException(InvalidTransactionException e, HttpServletRequest request) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }
}
