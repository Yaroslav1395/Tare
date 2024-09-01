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
    Optional<RoleEntity> findByRole(String role);

    @Query("SELECT r FROM RoleEntity r JOIN r.users u WHERE u.id = :userId")
    Set<RoleEntity> findRolesByUserId(@Param("userId") Integer userId);
}
