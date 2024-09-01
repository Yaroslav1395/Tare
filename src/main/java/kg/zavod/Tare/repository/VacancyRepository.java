package kg.zavod.Tare.repository;

import kg.zavod.Tare.domain.VacancyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacancyRepository extends JpaRepository<VacancyEntity, Integer> {
}
