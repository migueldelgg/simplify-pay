package SimplifyPay.adapters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import SimplifyPay.domain.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{
    
}
