package mateus_dev.picpay_simplificado.transaction;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transaction")
public class TransactionController {
  private final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @PostMapping
  public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
    return ResponseEntity.ok(transactionService.create(transaction));
  }

  @GetMapping
  public ResponseEntity<List<Transaction>> getTransactions() {
    return ResponseEntity.ok(transactionService.list());
  }
}
