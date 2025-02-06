package kg.zavod.Tare.repository.delivery;

import kg.zavod.Tare.domain.delivery.DistrictCapacityPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictCapacityPriceRepository extends JpaRepository<DistrictCapacityPriceEntity, Integer> {
}
