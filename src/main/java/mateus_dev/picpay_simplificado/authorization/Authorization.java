package mateus_dev.picpay_simplificado.authorization;

public record Authorization(
        String status
) {
  public boolean isAuthorized() {
    return status.equals("success");
  }
}
