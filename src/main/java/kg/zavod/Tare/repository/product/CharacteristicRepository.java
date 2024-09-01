package kg.zavod.Tare.repository.product;

import kg.zavod.Tare.domain.product.CharacteristicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacteristicRepository extends JpaRepository<CharacteristicEntity, Integer> {
}
