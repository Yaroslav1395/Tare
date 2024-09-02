package kg.zavod.Tare.repository.delivery;

import kg.zavod.Tare.domain.delivery.DivisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DivisionRepository extends JpaRepository<DivisionEntity, Integer> {
    /**
     * Метод позволяет найти территориальное деление по названию
     * @param name - название
     * @return - территориальное деление
     */
    Optional<DivisionEntity> findByName(String name);

    /**
     * Метод позволяет найти территориальное деление по названию исключая переданный id деления
     * @param name - название деления
     * @param id - id деления которое нужно исключить
     * @return - деление
     */
    Optional<DivisionEntity> findByNameAndIdNot(String name, Integer id);
}
