package mateus_dev.picpay_simplificado.authorization;

import org.apache.kafka.common.errors.AuthorizationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthorizationExceptionHandler {
  @ExceptionHandler
  public ResponseEntity<String> handleAuthorizationException(AuthorizationException ex){
    return ResponseEntity.status(403).body(ex.getMessage());
  }
}
