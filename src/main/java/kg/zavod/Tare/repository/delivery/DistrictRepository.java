package kg.zavod.Tare.repository.delivery;

import kg.zavod.Tare.domain.delivery.DistrictEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<DistrictEntity, Integer> {
    /**
     * Метод находит район по названию и id территориального деления
     * @param name - название
     * @param divisionId - id территориального деления
     * @return - район
     */
    Optional<DistrictEntity> findByNameAndDivisionId(String name, Integer divisionId);

    /**
     * Метод находит район по названию и id территориального деления исключая id переданного района
     * @param name - название
     * @param divisionId - id территориального деления
     * @param districtId - id района
     * @return - район
     */
    Optional<DistrictEntity> findByNameAndDivisionIdAndIdIsNot(String name, Integer divisionId, Integer districtId);
}
