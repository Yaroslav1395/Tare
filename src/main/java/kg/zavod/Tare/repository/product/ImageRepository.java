package kg.zavod.Tare.repository.product;

import kg.zavod.Tare.domain.product.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
    /**
     * Метод позволяет найти картинки по id продукта
     * @param productId - id продукта
     * @return - список картинок продукта
     */
    List<ImageEntity> findAllByProductId(Integer productId);
}
