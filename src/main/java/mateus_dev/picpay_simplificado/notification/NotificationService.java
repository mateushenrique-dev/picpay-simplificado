package mateus_dev.picpay_simplificado.notification;

import mateus_dev.picpay_simplificado.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
  private final NotificationProducer notificationProducer;
  private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

  public NotificationService(NotificationProducer notificationProducer) {
    this.notificationProducer = notificationProducer;
  }
  
  public void notify(Transaction transaction) {
    logger.info("Sending notification");
    notificationProducer.sendNotification(transaction);
  }
}
