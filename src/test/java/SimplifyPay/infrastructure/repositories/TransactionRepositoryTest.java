package SimplifyPay.infrastructure.repositories;

import SimplifyPay.domain.entities.TransactionEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface TransactionRepositoryTest extends JpaRepository<TransactionEntity, UUID>  {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM \"transaction\" WHERE payer_wallet_id = :inputId", nativeQuery = true)
    void deleteByPayerWalletId(@Param("inputId") UUID inputId);

}
