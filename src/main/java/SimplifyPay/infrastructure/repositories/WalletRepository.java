package SimplifyPay.infrastructure.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import SimplifyPay.domain.entities.WalletEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, UUID>{
    @Query(
            value = "SELECT * FROM wallet WHERE user_id = :inputId FOR UPDATE",
            nativeQuery = true
    )
    Optional<WalletEntity> findByUserId(Integer inputId);
}
