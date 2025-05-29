package mateus_dev.picpay_simplificado.transaction;

import mateus_dev.picpay_simplificado.authorization.AuthorizerService;
import mateus_dev.picpay_simplificado.notification.NotificationService;
import mateus_dev.picpay_simplificado.wallet.Wallet;
import mateus_dev.picpay_simplificado.wallet.WalletRepository;
import mateus_dev.picpay_simplificado.wallet.WalletType;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {
  private final TransactionRepository transactionRepository;
  private final WalletRepository walletRepository;
  private final AuthorizerService authorizerService;
  private final NotificationService notificationService;

  public TransactionService(
          TransactionRepository transactionRepository,
          WalletRepository walletRepository,
          AuthorizerService authorizerService,
          NotificationService notificationService
  ) {
    this.transactionRepository = transactionRepository;
    this.walletRepository = walletRepository;
    this.authorizerService = authorizerService;
    this.notificationService = notificationService;
  }

  @Retryable(
          value = { OptimisticLockingFailureException.class },
          maxAttempts = 3,
          backoff = @Backoff(delay = 100)
  )
  @Transactional
  public Transaction create(Transaction transaction) {
    validate(transaction);

    var newTransaction = transactionRepository.save(transaction);

    var walletOptPayer = walletRepository.findById(transaction.payer());
    var walletOptPayee = walletRepository.findById(transaction.payee());

    if (walletOptPayer.isPresent() && walletOptPayee.isPresent()) {
      var walletPayer = walletOptPayer.get();
      var walletPayee = walletOptPayee.get();

      walletRepository.save(walletPayer.debit(transaction.value()));
      walletRepository.save(walletPayee.credit(transaction.value()));
    }

    authorizerService.authorize(newTransaction);
    notificationService.notify(transaction);

    return newTransaction;
  }

  @Recover
  public void recover(OptimisticLockingFailureException e) {
    throw new RuntimeException("Não foi possível completar a transferência após múltiplas tentativas", e);
  }

  public void validate(Transaction transaction) {
    walletRepository.findById(transaction.payee())
            .map(payee -> walletRepository.findById(transaction.payer())
                    .map(payer -> isTransactionValid(transaction, payer) ? transaction : null)
                    .orElseThrow(
                            () -> new InvalidTransactionException("Invalid transaction - %s".formatted(transaction))
                    )
            )
            .orElseThrow(
                    () -> new InvalidTransactionException("Invalid transaction - %s".formatted(transaction))
            );
  }

  private boolean isTransactionValid(Transaction transaction, Wallet payer) {
    return payer.type() == WalletType.COMUM.getValue() &&
            payer.balance().compareTo(transaction.value()) >= 0 &&
            !payer.id().equals(transaction.payee());
  }

  public List<Transaction> list() {
    return transactionRepository.findAll();
  }
}
