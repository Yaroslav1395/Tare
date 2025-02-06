package kg.zavod.Tare.repository;

import kg.zavod.Tare.domain.PartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PartnerRepository extends JpaRepository<PartnerEntity, Integer> {
}
