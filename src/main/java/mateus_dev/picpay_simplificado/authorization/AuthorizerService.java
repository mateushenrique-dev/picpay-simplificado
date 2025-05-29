package mateus_dev.picpay_simplificado.authorization;

import mateus_dev.picpay_simplificado.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AuthorizerService {
  private RestClient restClient;
  private static final Logger log = LoggerFactory.getLogger(AuthorizerService.class);

  public AuthorizerService(RestClient.Builder builder) {
    this.restClient = builder
            .baseUrl("https://util.devi.tools/api/v2/authorize")
            .build();
  }

  public void authorize(Transaction transaction) {
    log.info("Authorizing transaction: {}", transaction);

    var response = restClient.get()
            .retrieve()
            .toEntity(Authorization.class);

    if (response.getStatusCode().isError() || !response.getBody().isAuthorized()) {
      throw new UnauthorizedTransactionException("Unauthorized transaction");
    }

    log.info("Transaction authorized: {}", transaction);
  }
}
