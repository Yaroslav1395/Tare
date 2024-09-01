package kg.zavod.Tare.repository.delivery;

import kg.zavod.Tare.domain.delivery.DistrictEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<DistrictEntity, Integer> {
}
