package kg.zavod.Tare.repository.category;

import kg.zavod.Tare.domain.category.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    /**
     * Метод позволяет найти категорию по названию
     * @param name - название
     * @return - категория
     */
    Optional<CategoryEntity> findByName(String name);

    /**
     * Метод позволяет найти категорию по названию исключая ту категорию,
     * которая имеет переданный id
     * @param name - название категории
     * @param id - id категории
     * @return - категория
     */
    Optional<CategoryEntity> findByNameAndIdIsNot(String name, Integer id);
}
