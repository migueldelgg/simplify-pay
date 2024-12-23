package SimplifyPay.adapters.repositories;

import SimplifyPay.domain.entities.UserEntity;
import SimplifyPay.domain.entities.WalletEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepositoryTest extends JpaRepository<UserEntity, Integer> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM \"user\" WHERE id = :inputId", nativeQuery = true)
    void deleteByUserId(@Param("inputId") Integer inputId);
}
