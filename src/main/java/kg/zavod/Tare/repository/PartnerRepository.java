package kg.zavod.Tare.repository;

import kg.zavod.Tare.domain.PartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerRepository extends JpaRepository<PartnerEntity, Integer> {
    /**
     * Метод меняет состояние активности партнера
     * @param id - id партнера
     * @param isActive - флаг активности
     * @return - состояние партнера
     */
    @Modifying
    @Query("UPDATE PartnerEntity p SET p.isActive = :isActive WHERE p.id = :id")
    int updateIsActiveById(@Param("id") Integer id, @Param("isActive") Boolean isActive);

    /**
     * Метод позволяет найти всех активных партнеров
     * @return - список активных новостей
     */
    List<PartnerEntity> findAllByIsActiveTrue();
}
