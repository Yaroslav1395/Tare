package kg.zavod.Tare.repository.product;

import kg.zavod.Tare.domain.product.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    @Query(value = "WITH RankedProducts AS (" +
            "SELECT " +
                "p.id, " +
                "p.name, " +
                "p.subcategory_id, " +
                "p.id_from_factory_bd, " +
                "ROW_NUMBER() OVER (PARTITION BY p.subcategory_id ORDER BY p.id) AS row_num " +
                "FROM ZAVOD.products p " +
            ") " +
            "SELECT * " +
            "FROM RankedProducts " +
            "WHERE row_num <= 1; ", nativeQuery = true)
    List<ProductEntity> findAllSubcategoryProductWithLimit6(List<Integer> subcategoriesId);

    List<ProductEntity> findAllBySubcategoryId(Integer subcategoryId);
    /**
     * Метод позволяет получить из базы продукты в подкатегории с пагинацией
     * @param subcategoryId - id подкатегории
     * @param pageable - пагинация
     * @return - страница с продуктами
     */
    Page<ProductEntity> findAllBySubcategoryId(Integer subcategoryId, Pageable pageable);
}
