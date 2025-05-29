package mateus_dev.picpay_simplificado.transaction;

public class InvalidTransactionException extends RuntimeException {
  public InvalidTransactionException(String message) {
    super(message);
  }
}
