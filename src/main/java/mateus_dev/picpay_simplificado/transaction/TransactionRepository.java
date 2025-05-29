package mateus_dev.picpay_simplificado.transaction;

import org.springframework.data.repository.ListCrudRepository;

public interface TransactionRepository extends ListCrudRepository<Transaction, Long> {
}
