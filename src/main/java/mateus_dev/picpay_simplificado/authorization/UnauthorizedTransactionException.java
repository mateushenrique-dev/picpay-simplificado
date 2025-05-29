package mateus_dev.picpay_simplificado.authorization;

public class UnauthorizedTransactionException extends RuntimeException {
  public UnauthorizedTransactionException(String message) {
    super(message);
  }
}
