package mateus_dev.picpay_simplificado.notification;

import mateus_dev.picpay_simplificado.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class NotificationConsumer {
  private RestClient restClient;
  private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);

  public NotificationConsumer(RestClient.Builder builder) {
    this.restClient = builder
            .baseUrl("https://util.devi.tools/api/v1/notify")
            .build();
  }

  @KafkaListener(topics = "transaction-notification", groupId = "picpay-desafio-backend")
  public void receiveNotification(Transaction transaction) {
    log.info("Notification received: {}", transaction);
  }
}
