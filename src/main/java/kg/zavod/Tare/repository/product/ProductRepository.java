package kg.zavod.Tare.repository.product;

import kg.zavod.Tare.domain.product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    /**
     * Метод позволяет найти по 2 продукта в каждой подкатегории категорий бутылки и банки
     * @return - список продуктов
     */
    @Query(value = """
                        SELECT * FROM (
                            SELECT p.*, ROW_NUMBER() OVER (PARTITION BY p.subcategory_id ORDER BY p.id) as row_num
                            FROM Zavod.products p
                            WHERE p.subcategory_id IN (SELECT s.id FROM Zavod.subcategories s WHERE s.category_id IN (1, 2))
                        ) AS filtered_products
                        WHERE row_num <= 2
                    """, nativeQuery = true)
    List<ProductEntity> findProductInAllSubcategoryForBottleAndJarCategoryWithLimit();

    /**
     * Метод позволяет найти продукты по имени
     * @param name - имя
     * @return - список продуктов
     */
    List<ProductEntity> findByNameContainingIgnoreCase(String name);

    /**
     * Метод позволяет найти продукты по id подкатегории
     * @param subcategoryId - id подкатегории
     * @return - список продуктов
     */
    List<ProductEntity> findAllBySubcategoryId(Integer subcategoryId);
}
