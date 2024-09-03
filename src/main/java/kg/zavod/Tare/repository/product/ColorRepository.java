package kg.zavod.Tare.repository.product;

import kg.zavod.Tare.domain.product.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<ColorEntity, Integer> {
    /**
     * Метод находит цвет по названию или hex коду
     * @param name - название
     * @param hexCode - hex код
     * @return - цвет
     */
    Optional<ColorEntity> findByNameOrHexCode(String name, String hexCode);

    /**
     * Метод находит цвет по названию или hex коду исключая цвет с переданным id
     * @param name - название
     * @param hexCode - hex код
     * @param id - id цвета
     * @return - цвет
     */
    Optional<ColorEntity> findByNameOrHexCodeAndIdNot(String name, String hexCode, Integer id);
}
