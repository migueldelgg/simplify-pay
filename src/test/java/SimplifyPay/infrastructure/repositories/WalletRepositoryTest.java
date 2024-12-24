package SimplifyPay.infrastructure.repositories;

import SimplifyPay.domain.entities.WalletEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepositoryTest extends JpaRepository<WalletEntity, UUID> {

    @Query(value = "SELECT * FROM wallet WHERE user_id = :inputId", nativeQuery = true)
    Optional<WalletEntity> getByUserId(@Param("inputId") Integer inputId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE wallet SET balance = :value, updated_at = NOW() WHERE user_id = :inputId", nativeQuery = true)
    void setBalance(@Param("inputId") Integer inputId, @Param("value") BigDecimal value);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM wallet WHERE user_id = :inputId", nativeQuery = true)
    void deleteByUserId(@Param("inputId") Integer inputId);
}