package mateus_dev.picpay_simplificado.notification;

public record Notification(
        String status
) {
  public boolean wasSent() {
    return status.equals("success");
  }
}
