package kg.zavod.Tare.repository.user;

import kg.zavod.Tare.domain.user.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    /**
     * Метод позволяет найти роль по названию
     * @param role - название роли
     * @return - роль
     */
    Optional<RoleEntity> findByRole(String role);

    /**
     * Метод позволяет найти роль по названию исключая роль с переданным id
     * @param role - название роли
     * @param id - id роли
     * @return - роль
     */
    Optional<RoleEntity> findByRoleAndIdIsNot(String role, Integer id);

}
