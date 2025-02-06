package kg.zavod.Tare.repository.category;

import kg.zavod.Tare.domain.category.SubcategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubcategoryRepository extends JpaRepository<SubcategoryEntity, Integer> {

    /**
     * Метод позволяет найти все подкатегории по id категории
     * @param categoryId - id категории
     * @return - подкатегории
     */
    List<SubcategoryEntity> findAllByCategoryId(Integer categoryId);

    /**
     * Метод позволяет найти подкатегорию по названию и id категории
     * @param name - название
     * @param categoryId - id подкатегории
     * @return - подкатегория
     */
    Optional<SubcategoryEntity> findByNameAndCategoryId(String name, Integer categoryId);

    /**
     * Метод позволяет найти подкатегорию по названию, исключая переданный id подкатегории в категории
     * @param name - название
     * @param subcategoryId - id подкатегории которую надо исключить
     * @param categoryId - категория в которой находится подкатегория
     * @return - подкатегория
     */
    Optional<SubcategoryEntity> findByNameAndIdIsNotAndCategoryId(String name, Integer subcategoryId, Integer categoryId);
}
