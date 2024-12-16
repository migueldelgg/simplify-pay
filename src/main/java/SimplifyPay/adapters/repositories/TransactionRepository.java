package SimplifyPay.adapters.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import SimplifyPay.domain.entities.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID>{
    
}
