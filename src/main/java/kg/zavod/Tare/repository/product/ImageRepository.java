package kg.zavod.Tare.repository.product;

import kg.zavod.Tare.domain.product.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM ImageEntity i WHERE i.id IN :ids")
    void deleteAllByIdIn(@Param("ids") List<Integer> ids);

    /**
     * Метод позволяет найти картинки по id продукта
     * @param productId - id продукта
     * @return - список картинок продукта
     */
    List<ImageEntity> findAllByProductId(Integer productId);
}
