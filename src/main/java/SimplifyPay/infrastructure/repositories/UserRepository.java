package SimplifyPay.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import SimplifyPay.domain.entities.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{
}
