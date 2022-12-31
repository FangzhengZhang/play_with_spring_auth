package cat.frank.playWithAuthJWT.repository;

import cat.frank.playWithAuthJWT.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findAllByUsername(String username);
    Boolean existsByUsername(String username);
}
