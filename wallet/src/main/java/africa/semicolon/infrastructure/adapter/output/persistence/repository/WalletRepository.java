package africa.semicolon.infrastructure.adapter.output.persistence.repository;

import africa.semicolon.infrastructure.adapter.output.persistence.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<WalletEntity, Long> {
}
