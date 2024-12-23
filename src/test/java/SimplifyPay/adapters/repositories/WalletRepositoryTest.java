package SimplifyPay.adapters.repositories;

import SimplifyPay.domain.entities.WalletEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface WalletRepositoryTest extends JpaRepository<WalletEntity, UUID> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE wallet SET balance = 1000.00, updated_at = NOW() WHERE user_id = :inputId", nativeQuery = true)
    void setBalance(@Param("inputId") Integer inputId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM wallet WHERE user_id = :inputId", nativeQuery = true)
    void deleteByUserId(@Param("inputId") Integer inputId);
}