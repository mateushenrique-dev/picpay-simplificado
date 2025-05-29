package mateus_dev.picpay_simplificado.notification;

import mateus_dev.picpay_simplificado.transaction.Transaction;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {
  private final KafkaTemplate<String, Transaction> kafkaTemplate;

  public NotificationProducer(KafkaTemplate<String, Transaction> template) {
    this.kafkaTemplate = template;
  }

  public void sendNotification(Transaction transaction) {
    kafkaTemplate.send("transaction-notification", transaction);
  }
}
