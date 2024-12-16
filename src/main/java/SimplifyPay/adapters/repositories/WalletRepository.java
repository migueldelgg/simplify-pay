package SimplifyPay.adapters.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import SimplifyPay.domain.entities.WalletEntity;

public interface WalletRepository extends JpaRepository<WalletEntity, UUID>{
    
}
