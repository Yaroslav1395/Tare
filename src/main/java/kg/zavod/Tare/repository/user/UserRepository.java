package kg.zavod.Tare.repository.user;

import kg.zavod.Tare.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    /**
     * Метод находит пользователя по его имени
     * @param userName - имя пользователя
     * @return - пользователь
     */
    Optional<UserEntity> findByUsername(String userName);
}
