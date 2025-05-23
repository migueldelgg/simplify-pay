package SimplifyPay.infrastructure.repositories;

import SimplifyPay.domain.entities.UserEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepositoryTest extends JpaRepository<UserEntity, Integer> {

    @Modifying
    @Query(value = "DELETE FROM \"user\" WHERE id = :inputId", nativeQuery = true)
    void deleteByUserId(@Param("inputId") Integer inputId);
}
