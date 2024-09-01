package kg.zavod.Tare.repository.delivery;

import kg.zavod.Tare.domain.delivery.CapacityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CapacityRepository extends JpaRepository<CapacityEntity, Integer> {
    /**
     * Метод ищет дубликат по лимитам допустимого объема
     * @param capacityFrom - объем от
     * @param capacityTo - объем до
     * @return - найденный допустимый объем
     */
    Optional<CapacityEntity> findByCapacityFromAndCapacityTo(Integer capacityFrom, Integer capacityTo);
}
