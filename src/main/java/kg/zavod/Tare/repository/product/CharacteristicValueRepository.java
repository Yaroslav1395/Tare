package kg.zavod.Tare.repository.product;

import kg.zavod.Tare.domain.product.ProductCharacteristicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacteristicValueRepository extends JpaRepository<ProductCharacteristicEntity, Integer> {
    /**
     * Метод найдет все характеристики продукта
     * @param productId - id продукта
     * @return - список характеристик
     */
    List<ProductCharacteristicEntity> findAllByProductId(Integer productId);
}
