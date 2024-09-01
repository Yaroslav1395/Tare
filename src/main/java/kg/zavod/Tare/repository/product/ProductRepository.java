package kg.zavod.Tare.repository.product;

import kg.zavod.Tare.domain.product.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    /**
     * Метод позволяет получить из базы продукты в подкатегории с пагинацией
     * @param subcategoryId - id подкатегории
     * @param pageable - пагинация
     * @return - страница с продуктами
     */
    Page<ProductEntity> findAllBySubcategoryId(Integer subcategoryId, Pageable pageable);
}
