package kg.zavod.Tare.repository.product;

import kg.zavod.Tare.domain.product.ProductCharacteristicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CharacteristicValueRepository extends JpaRepository<ProductCharacteristicEntity, Integer> {

    /**
     * Метод позволяет удалить характеристики по списку id
     * @param ids - список id
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM ProductCharacteristicEntity c WHERE c.id IN :ids")
    void deleteAllByIdIn(@Param("ids") List<Integer> ids);
}
