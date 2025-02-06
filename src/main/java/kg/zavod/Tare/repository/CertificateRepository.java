package kg.zavod.Tare.repository;

import kg.zavod.Tare.domain.CertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<CertificateEntity, Integer> {
    /**
     * Поиск сертификата по названию
     * @param name - название
     * @return - сертификат
     */
    Optional<CertificateEntity> findByName(String name);

    /**
     * Поиск сертификата по названию исключая названия сертификата с переданным id
     * @param name - название
     * @param id - id сертификата
     * @return - сертификат
     */
    Optional<CertificateEntity> findByNameAndIdIsNot(String name, Integer id);
}
