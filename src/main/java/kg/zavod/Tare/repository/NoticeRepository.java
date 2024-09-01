package kg.zavod.Tare.repository;

import kg.zavod.Tare.domain.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Integer> {
    /**
     * Метод меняет состояние активности новости
     * @param id - id новости
     * @param isActive - флаг активности
     * @return - состояние новости
     */
    @Modifying
    @Query("UPDATE NoticeEntity n SET n.isActive = :isActive WHERE n.id = :id")
    int updateIsActiveById(@Param("id") Integer id, @Param("isActive") Boolean isActive);

    /**
     * Метод позволяет найти все активные новости
     * @return - список активных новостей
     */
    List<NoticeEntity> findAllByIsActiveTrue();
}
