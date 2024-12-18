package SimplifyPay.adapters.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import SimplifyPay.domain.entities.WalletEntity;

public interface WalletRepository extends JpaRepository<WalletEntity, UUID>{

    @Query(value = "SELECT * FROM wallet WHERE user_id = :inputId", nativeQuery = true)
    WalletEntity findByUserId(Integer inputId);

}
