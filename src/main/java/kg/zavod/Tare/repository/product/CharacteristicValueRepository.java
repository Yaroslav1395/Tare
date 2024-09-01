package kg.zavod.Tare.repository.product;

import kg.zavod.Tare.domain.product.ProductCharacteristicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacteristicValueRepository extends JpaRepository<ProductCharacteristicEntity, Integer> {
}
