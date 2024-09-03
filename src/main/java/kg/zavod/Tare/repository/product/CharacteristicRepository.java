package kg.zavod.Tare.repository.product;

import kg.zavod.Tare.domain.product.CharacteristicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharacteristicRepository extends JpaRepository<CharacteristicEntity, Integer> {

    /**
     * Метод позволяет найти характеристику по названию
     * @param name - название
     * @return - характеристика
     */
    Optional<CharacteristicEntity> findByName(String name);

    /**
     * Метод позволяет найти характеристику по названию исключая характеристику с переданным id
     * @param name - название характеристики
     * @param id - id характеристики
     * @return - характеристика
     */
    Optional<CharacteristicEntity> findByNameAndIdIsNot(String name, Integer id);
}
