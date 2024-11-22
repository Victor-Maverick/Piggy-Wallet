package africa.semicolon.infrastructure.adapter.output.persistence.repository;

import africa.semicolon.infrastructure.adapter.output.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
}
