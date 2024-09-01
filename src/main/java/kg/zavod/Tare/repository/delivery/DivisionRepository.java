package kg.zavod.Tare.repository.delivery;

import kg.zavod.Tare.domain.delivery.DivisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DivisionRepository extends JpaRepository<DivisionEntity, Integer> {
}
