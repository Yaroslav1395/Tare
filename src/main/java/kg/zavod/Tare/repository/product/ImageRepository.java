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

    @Query(value = "SELECT * FROM ZAVOD.images i " +
            "WHERE i.product_id IN (" +
            "   SELECT p.id FROM ZAVOD.products p " +
            "   JOIN ZAVOD.subcategories s ON p.subcategory_id = s.id " +
            "   WHERE s.category_id IN :categoryIds " +
            "   LIMIT 6) " +
            "AND i.id = (SELECT MIN(img.id) FROM ZAVOD.images img WHERE img.product_id = i.product_id)",
            nativeQuery = true)
    List<ImageEntity> findImagesByCategoryIds(@Param("categoryIds") List<Integer> categoryIds);

    /*@Query("SELECT i FROM ImageEntity i " +
            "WHERE i.product IN (SELECT p FROM ProductEntity p " +
            "JOIN p.subcategory s " +
            "WHERE s.category.id IN :categoryIds) " +
            "AND i.id = (SELECT MIN(img.id) FROM ImageEntity img WHERE img.product.id = i.product.id)")
    List<ImageEntity> findImagesByCategoryIds(@Param("categoryIds") List<Integer> categoryIds);*/

    /**
     * Метод позволяет найти по одной картинке для каждого продукта
     * @return - список картинок (по одной для каждого продукта)
     */
    @Query("SELECT i FROM ImageEntity i WHERE i.id IN " +
            "(SELECT MIN(image.id) FROM ImageEntity image " +
            "JOIN image.product p GROUP BY p.id)")
    List<ImageEntity> findOneImageForEachProduct();


    /**
     * Метод позволяет найти картинки по id продукта
     * @param productId - id продукта
     * @return - список картинок продукта
     */
    List<ImageEntity> findAllByProductId(Integer productId);
}
