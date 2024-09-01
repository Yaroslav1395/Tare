package kg.zavod.Tare.repository.delivery;

import kg.zavod.Tare.domain.delivery.DistrictCapacityPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistrictCapacityPriceRepository extends JpaRepository<DistrictCapacityPriceEntity, Integer> {
    /**
     * Метод находит сущность цены доставки исходя из переданных параметров. Для выявления дубликатов
     * @param capacityId - id допустимого объема
     * @param districtId - id райна доставки
     * @return - найденная цена доставки
     */
    Optional<DistrictCapacityPriceEntity> findByCapacityIdAndDistrictId(Integer capacityId, Integer districtId);

    /**
     * Метод позволяет найти все цены доставки для определенного района
     * @param districtId - id района
     * @return - список цен доставки в опеделенный район
     */
    List<DistrictCapacityPriceEntity> findAllByDistrictId(Integer districtId);

    /**
     * Метод позволяет найти все цены доставки для определенного допустимого объема
     * @param capacityId - id допустимого объема
     * @return - список цен доставки для определенного района
     */
    List<DistrictCapacityPriceEntity> findAllByCapacityId(Integer capacityId);
}
